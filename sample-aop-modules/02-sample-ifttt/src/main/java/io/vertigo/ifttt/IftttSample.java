package io.vertigo.ifttt;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertigo.core.lang.WrappedException;

/**
 *
 * @author xdurand
 *
 */
public class IftttSample {

	private static final Logger LOGGER = LogManager.getLogger(IftttSample.class);

	private static final String IFTTT = "ifttt.properties";

	public static void main(final String[] args) {

		System.setProperty("https.proxyHost", "172.20.0.9");
		System.setProperty("https.proxyPort", "3128");

		final Properties prop = new Properties();

		String url = null;
		try (InputStream input = IftttSample.class.getClassLoader().getResourceAsStream(IFTTT)) {
			prop.load(input);
			url = prop.getProperty("url");
		} catch (final IOException e) {
			LOGGER.error("Error while loading " + IFTTT, e);
			throw WrappedException.wrap(e);
		}

		final Client client = ClientBuilder.newClient();
		final WebTarget resource = client.target(url);

		final Builder request = resource.request();
		request.accept(MediaType.APPLICATION_JSON);

		final Date now = new Date();
		final DateFormat format = new SimpleDateFormat("dd/MM HH:mm:ss");
		final String message = "Test IftttSample " + format.format(now);

		final MakerEvent postMessage = new MakerEvent();
		postMessage.setValue1("My message");
		postMessage.setValue2(message);

		final Response response = request.post(Entity.<MakerEvent> entity(postMessage, MediaType.APPLICATION_JSON));

		if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			LOGGER.info("Success! " + response.getStatus());
		} else {
			LOGGER.error("Error! " + response.getStatus());
		}

	}

}
