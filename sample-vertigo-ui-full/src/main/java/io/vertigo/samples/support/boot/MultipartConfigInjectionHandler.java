/*
 *
 *  Copyright (c) 2012. inTux.info.
 *
 *  This is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this software; if not, write to the Free
 *  Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 *  02111-1307 USA
 *  or on the web at: http://www.gnu.org/copyleft/lesser.html
 *
 *
 */

package io.vertigo.samples.support.boot;

import java.io.IOException;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Multipart Config Injection Handler.
 *
 * <p>
 * This handler can be applied to any Jetty Handler. It checks if the
 * current request is a multipart request, if so, it sets the request
 * attribute named {@link Request#__MULTIPART_CONFIG_ELEMENT} to a
 * {@link MultipartConfigElement} to enable multipart requests.
 *
 * <p>
 * MultipartConfigInjectionHandler ensures that the parts are deleted after the
 * {@link #handle(String, Request, HttpServletRequest, HttpServletResponse)}
 * method is called.
 *
 * <p>
 * Ensure that no other handlers sit above this handler which may wish to do
 * something with the multipart parts, as the saved parts will be deleted on the return
 * from
 * {@link #handle(String, Request, HttpServletRequest, HttpServletResponse)}.
 */
public class MultipartConfigInjectionHandler extends HandlerWrapper {
	private static final Logger LOG = LogManager.getLogger(MultipartConfigInjectionHandler.class);
	private static final String MULTIPART_FORMDATA_TYPE = "multipart/form-data";
	private static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement(
			System.getProperty("java.io.tmpdir"));

	public static boolean isMultipartRequest(final ServletRequest request) {
		return request.getContentType() != null
				&& request.getContentType().startsWith(MULTIPART_FORMDATA_TYPE);
	}

	/**
	 * If you want to have multipart support in your handler, call this method each time
	 * your doHandle method is called (prior to calling getParameter).
	 *
	 * Servlet 3.0 include support for Multipart data with its
	 * {@link HttpServletRequest#getPart(String)} & {@link HttpServletRequest#getParts()}
	 * methods, but the spec says that before you can use getPart, you must have specified a
	 * {@link MultipartConfigElement} for the Servlet.
	 *
	 * <p>
	 * This is normally done through the use of the MultipartConfig annotation of the
	 * servlet in question, however these annotations will not work when specified on
	 * Handlers.
	 *
	 * <p>
	 * The workaround for enabling Multipart support in handlers is to define the
	 * MultipartConfig attribute for the request which in turn will be read out in the
	 * getPart method.
	 *
	 * @see <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=395000#c0">Jetty Bug
	 *      tracker - Jetty annotation scanning problem (servlet workaround) </a>
	 * @see <a href="http://dev.eclipse.org/mhonarc/lists/jetty-users/msg03294.html">Jetty
	 *      users mailing list post.</a>
	 */
	public static void enableMultipartSupport(final HttpServletRequest request) {
		request.setAttribute(Request.__MULTIPART_CONFIG_ELEMENT, MULTI_PART_CONFIG);
	}

	@Override
	public void handle(final String target, final Request baseRequest, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException, ServletException {
		final boolean multipartRequest = "POST".equals(request.getMethod())
				&& isMultipartRequest(request);
		if (multipartRequest) {
			enableMultipartSupport(request);
		}

		try {
			super.handle(target, baseRequest, request, response);
		} finally {
			if (multipartRequest) {
				// a multipart request to a servlet will have the parts cleaned up correctly, but
				// the repeated call to deleteParts() here will safely do nothing.
				request.getParts()
						.stream()
						.forEach(part -> {
							try {
								Objects.requireNonNull(part).delete();
							} catch (final IOException e) {
								LOG.error("Error while deleting multipart request parts " + part.getName(), e);
							}
						});
			}
		}
	}
}
