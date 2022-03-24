/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2022, Vertigo.io, team@vertigo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.samples.support.boot;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Collections;

import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.SpringServletContainerInitializer;

import io.vertigo.samples.support.config.VuiVSpringWebApplicationInitializer;

public final class BootSampleVui {

	public static void main(final String[] args) throws Exception {
		startServer();
		server.join();
	}

	private static ClassLoader getUrlClassLoader() {
		return new URLClassLoader(new URL[0], BootSampleVui.class.getClassLoader());
	}

	private static final int port = 18080;
	private static Server server;

	private static void startServer() throws IOException, Exception {
		server = new Server(port);
		final WebAppContext context = new WebAppContext(Paths.get(BootSampleVui.class.getClassLoader().getResource("webapp/").toURI()).toString(), "/sample");
		System.setProperty("org.apache.jasper.compiler.disablejsr199", "false");
		context.setAttribute("jacoco.exclClassLoaders", "*");

		context.setAttribute("javax.servlet.context.tempdir", getScratchDir());

		final ContainerInitializer springInitializer = new ContainerInitializer(new SpringServletContainerInitializer(), null);
		springInitializer.addApplicableTypeName(VuiVSpringWebApplicationInitializer.class.getCanonicalName());
		context.setAttribute("org.eclipse.jetty.containerInitializers", Collections.singletonList(springInitializer));
		context.addBean(new ServletContainerInitializersStarter(context), true);
		context.setClassLoader(getUrlClassLoader());
		context.setClassLoader(new WebAppClassLoader(BootSampleVui.class.getClassLoader(), context));

		final MultipartConfigInjectionHandler multipartConfigInjectionHandler = new MultipartConfigInjectionHandler();
		multipartConfigInjectionHandler.setHandler(context);
		server.setHandler(multipartConfigInjectionHandler);
		server.start();
	}

	private static File getScratchDir() throws IOException {
		final File tempDir = new File(System.getProperty("java.io.tmpdir"));
		final File scratchDir = new File(tempDir.toString(), "embedded-jetty-html");

		if (!scratchDir.exists()) {
			if (!scratchDir.mkdirs()) {
				throw new IOException("Unable to create scratch directory: " + scratchDir);
			}
		}
		return scratchDir;
	}

}
