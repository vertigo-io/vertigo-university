package io.vertigo.demo.boot.initializer;

import java.util.Date;

import javax.inject.Inject;

import io.vertigo.core.component.ComponentInitializer;
import io.vertigo.demo.jobs.job.ReloadMdCacheJob;
import io.vertigo.tempo.job.metamodel.JobDefinition;
import io.vertigo.tempo.scheduler.SchedulerManager;

/**
 * Initialisation du manager des jobs.
 * @author pchretien
 * @version $Id: JobManagerInitializer.java,v 1.7 2014/08/04 16:57:50 npiedeloup Exp $
 */
public final class JobManagerInitializer implements ComponentInitializer {

	@Inject
	private SchedulerManager schedulerManager;

	/** {@inheritDoc} */
	@Override
	public void init() {
		final JobDefinition reloadMdJobDefinition = new JobDefinition("RELOAD_MD_CACHE", ReloadMdCacheJob.class);
		schedulerManager.scheduleAtDate(reloadMdJobDefinition, new Date(System.currentTimeMillis() + 15 * 1000));// tout de suite+30s
		schedulerManager.scheduleEverySecondInterval(reloadMdJobDefinition, 120);// ET toutes les 2 minutes
	}
}
