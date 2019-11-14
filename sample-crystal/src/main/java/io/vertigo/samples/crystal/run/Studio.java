package io.vertigo.samples.crystal.run;

import javax.inject.Inject;

import io.vertigo.core.node.AutoCloseableApp;
import io.vertigo.core.util.InjectorUtil;
import io.vertigo.samples.crystal.config.SampleStudioConfigBuilder;
import io.vertigo.studio.mda.MdaManager;

public class Studio {
	@Inject
	private MdaManager mdaManager;

	public static void main(final String[] args) {
		try (final AutoCloseableApp app = new AutoCloseableApp(new SampleStudioConfigBuilder().build())) {
			final Studio sample = new Studio();
			InjectorUtil.injectMembers(sample);
			//-----
			sample.cleanGenerate();
		}
	}

	void cleanGenerate() {
		mdaManager.clean();
		mdaManager.generate().displayResultMessage(System.out);
	}
}
