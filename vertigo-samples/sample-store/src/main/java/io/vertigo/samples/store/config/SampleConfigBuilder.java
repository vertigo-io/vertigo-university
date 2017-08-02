package io.vertigo.samples.store.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.commons.plugins.cache.memory.MemoryCachePlugin;
import io.vertigo.core.param.Param;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.dynamo.plugins.store.datastore.sql.SqlDataStorePlugin;

public class SampleConfigBuilder {
	public AppConfig build() {
		return AppConfig.builder()
				.beginBoot()
				.withLocales("FR_fr")
				.endBoot()
				.addModule(new CommonsFeatures()
						.withCache(MemoryCachePlugin.class)
						.build())
				.addModule(new DynamoFeatures()
						.withStore()
						.withSqlDataBase()
						.addDataStorePlugin(SqlDataStorePlugin.class, Param.of("sequencePrefix", "SEQ_"))
						.build())
				.build();
	}
}
