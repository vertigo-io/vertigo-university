package io.vertigo.samples.store.config;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.dynamo.plugins.store.datastore.postgresql.PostgreSqlDataStorePlugin;

public class SampleConfigBuilder {
	public AppConfig build() {
		return new AppConfigBuilder()
				.beginModule(DynamoFeatures.class)
				.withStore()
				.getModuleConfigBuilder()
				.beginPlugin(PostgreSqlDataStorePlugin.class)
				.addParam("sequencePrefix", "SEQ_")
				.endPlugin()
				.endModule()
				.build();
	}
}
