package io.vertigo.demo.boot.initializer;

import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;

import io.vertigo.core.component.ComponentInitializer;
import io.vertigo.demo.jobs.job.ReloadMdCacheJob;
import io.vertigo.orchestra.definitions.ProcessDefinition;
import io.vertigo.orchestra.services.OrchestraServices;

/**
 * Initialisation du manager des jobs.
 * @author pchretien
 * @version $Id: JobManagerInitializer.java,v 1.7 2014/08/04 16:57:50 npiedeloup Exp $
 */
public final class JobManagerInitializer implements ComponentInitializer {

	@Inject
	private OrchestraServices orchestraServices;

	/** {@inheritDoc} */
	@Override
	public void init() {
		final ProcessDefinition reloadMdJobDefinition = ProcessDefinition.legacyBuilder("RELOAD_MD_CACHE", ReloadMdCacheJob.class)
				.withCronExpression("0 */2 * * * ?")
				.build();
		orchestraServices.getScheduler().scheduleAt(reloadMdJobDefinition, new Date(System.currentTimeMillis() + 15 * 1000), Collections.emptyMap());// tout de suite+30s
	}
}
