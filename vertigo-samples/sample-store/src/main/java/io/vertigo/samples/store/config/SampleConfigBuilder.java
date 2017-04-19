package io.vertigo.samples.store.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.core.param.Param;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.dynamo.plugins.store.datastore.sql.SqlDataStorePlugin;

public class SampleConfigBuilder {
	public AppConfig build() {
		return new AppConfigBuilder()
				.addModule(new DynamoFeatures()
						.withStore()
						.addDataStorePlugin(SqlDataStorePlugin.class, Param.create("sequencePrefix", "SEQ_"))
						.build())
				.build();
	}
}
