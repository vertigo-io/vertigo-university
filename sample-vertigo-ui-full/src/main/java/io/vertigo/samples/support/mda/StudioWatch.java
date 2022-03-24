package io.vertigo.samples.support.mda;

import java.net.MalformedURLException;
import java.nio.file.Paths;

import io.vertigo.core.lang.WrappedException;
import io.vertigo.studio.tools.VertigoStudioMda;

public class StudioWatch {

	public static void main(final String[] args) {
		try {
			VertigoStudioMda.main(new String[] { "watch", Paths.get("studio-config.yaml").toUri().toURL().toExternalForm() });
		} catch (final MalformedURLException e) {
			throw WrappedException.wrap(e);
		}
	}
}
