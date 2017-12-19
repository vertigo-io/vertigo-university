package io.vertigo.samples.crystal.run;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;

import com.google.gson.Gson;

import io.vertigo.app.AutoCloseableApp;
import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.DefinitionProviderConfig;
import io.vertigo.app.config.ModuleConfig;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.commons.plugins.cache.memory.MemoryCachePlugin;
import io.vertigo.core.component.di.injector.DIInjector;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.database.impl.sql.SqlAdapterSupplierPlugin;
import io.vertigo.database.impl.sql.SqlDataBaseManagerImpl;
import io.vertigo.database.impl.sql.vendor.h2.H2DataBase;
import io.vertigo.database.plugins.sql.connection.c3p0.C3p0ConnectionProviderPlugin;
import io.vertigo.database.sql.SqlDataBaseManager;
import io.vertigo.database.sql.connection.SqlConnection;
import io.vertigo.database.sql.mapper.SqlAdapter;
import io.vertigo.database.sql.statement.SqlStatement;
import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.dynamo.impl.task.proxy.TaskProxyMethod;
import io.vertigo.dynamo.plugins.environment.DynamoDefinitionProvider;
import io.vertigo.dynamo.plugins.store.datastore.sql.SqlDataStorePlugin;
import io.vertigo.lang.WrappedException;
import io.vertigo.samples.SamplesPAO;
import io.vertigo.samples.crystal.dao.ActorDAO;
import io.vertigo.samples.crystal.dao.MovieDAO;
import io.vertigo.samples.crystal.dao.RoleDAO;

public class DemoSqlAdapter {

	@Inject
	private SqlDataBaseManager sqlDataBaseManager;

	public static void main(final String[] args) {
		final AppConfigBuilder appConfigBuilder = AppConfig.builder()
				.beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.endBoot()
				.addModule(new CommonsFeatures()
						.withCache(MemoryCachePlugin.class)
						.withScript()
						.build())
				.addModule(ModuleConfig.builder("database-custom")
						.addComponent(SqlDataBaseManager.class, SqlDataBaseManagerImpl.class)
						.addPlugin(C3p0ConnectionProviderPlugin.class,
								Param.of("dataBaseClass", H2DataBase.class.getName()),
								Param.of("jdbcDriver", org.h2.Driver.class.getName()),
								Param.of("jdbcUrl", "jdbc:h2:D:/atelier/database/formation_loaded_demo"))
						.addPlugin(DemoSqlApdapterProviderPlugin.class)
						.build())
				.addModule(new DynamoFeatures()
						.withStore()
						.addDataStorePlugin(SqlDataStorePlugin.class,
								Param.of("sequencePrefix", "SEQ_"))
						.build())
				//---- proxies (Level4)
				.addModule(ModuleConfig.builder("proxies")
						.addProxyMethod(TaskProxyMethod.class)
						.build())
				//----Definitions
				.addModule(ModuleConfig.builder("ressources")
						.addDefinitionProvider(DefinitionProviderConfig.builder(DynamoDefinitionProvider.class)
								.addDefinitionResource("kpr", "application.kpr")
								.build())
						.build())
				.addModule(ModuleConfig.builder("mineDAO")
						.build())
				.addModule(ModuleConfig.builder("services")
						.addComponent(MovieDAO.class)
						.addComponent(ActorDAO.class)
						.addComponent(RoleDAO.class)
						.addComponent(SamplesPAO.class)
						.build());
		try (final AutoCloseableApp app = new AutoCloseableApp(appConfigBuilder.build())) {
			final DemoSqlAdapter sample = new DemoSqlAdapter();
			DIInjector.injectMembers(sample, app.getComponentSpace());
			//-----
			sample.step1();
		}
	}

	void step1() {
		try {
			final SqlConnection connection = sqlDataBaseManager.getConnectionProvider(SqlDataBaseManager.MAIN_CONNECTION_PROVIDER_NAME).obtainConnection();
			final SqlStatement sqlStatement = SqlStatement
					.builder("select * from movie_demo where mov_id = #mov_id#")
					.bind("mov_id", Long.class, 3678598L)
					.build();
			final List<MovieDemo> result = sqlDataBaseManager.executeQuery(sqlStatement, MovieDemo.class, (Integer) 1, connection);

			final MovieDemo starWars = result.get(0);
			starWars.setTranslations(new Translations("Un nouvel espoir", "Una nueva esperanza", "A new hope"));
			final SqlStatement sqlUpdateStatement = SqlStatement
					.builder("update movie_demo set translations = #translations# where mov_id = #mov_id# ")
					.bind("mov_id", Long.class, 3678598L)
					.bind("translations", Translations.class, new Translations("Un nouvel espoir", "Una nueva esperanza", "A new hope"))
					.build();

			sqlDataBaseManager.executeUpdate(sqlUpdateStatement, connection);

			connection.commit();
			final List<MovieDemo> result2 = sqlDataBaseManager.executeQuery(sqlStatement, MovieDemo.class, (Integer) 1, connection);
			connection.rollback();
			connection.release();
			LogManager.getLogger(this.getClass()).info(result2.get(0));
		} catch (final Exception e) {
			throw WrappedException.wrap(e, "Can't exec script");
		}
	}

	public static final class MovieDemo implements Entity {
		private static final long serialVersionUID = 1L;

		private Long movId;
		private String name;
		private Integer year;
		private String imdbid;
		private Translations translations;

		/** {@inheritDoc} */
		@Override
		public URI<MovieDemo> getURI() {
			return DtObjectUtil.createURI(this);
		}

		/**
		 * Champ : ID.
		 * Récupère la valeur de la propriété 'Id'.
		 * @return Long movId <b>Obligatoire</b>
		 */
		@Field(domain = "DO_ID", type = "ID", required = true, label = "Id")
		public Long getMovId() {
			return movId;
		}

		/**
		 * Champ : ID.
		 * Définit la valeur de la propriété 'Id'.
		 * @param movId Long <b>Obligatoire</b>
		 */
		public void setMovId(final Long movId) {
			this.movId = movId;
		}

		/**
		 * Champ : DATA.
		 * Récupère la valeur de la propriété 'Code du pays'.
		 * @return String name <b>Obligatoire</b>
		 */
		@Field(domain = "DO_LABEL_LONG", required = true, label = "Code du pays")
		public String getName() {
			return name;
		}

		/**
		 * Champ : DATA.
		 * Définit la valeur de la propriété 'Code du pays'.
		 * @param name String <b>Obligatoire</b>
		 */
		public void setName(final String name) {
			this.name = name;
		}

		/**
		 * Champ : DATA.
		 * Récupère la valeur de la propriété 'AnnÃ©e'.
		 * @return Integer year
		 */
		@Field(domain = "DO_YEAR", label = "AnnÃ©e")
		public Integer getYear() {
			return year;
		}

		/**
		 * Champ : DATA.
		 * Définit la valeur de la propriété 'AnnÃ©e'.
		 * @param year Integer
		 */
		public void setYear(final Integer year) {
			this.year = year;
		}

		/**
		 * Champ : DATA.
		 * Récupère la valeur de la propriété 'Id Imdb'.
		 * @return String imdbid
		 */
		@Field(domain = "DO_LABEL", label = "Id Imdb")
		public String getImdbid() {
			return imdbid;
		}

		/**
		 * Champ : DATA.
		 * Définit la valeur de la propriété 'Id Imdb'.
		 * @param imdbid String
		 */
		public void setImdbid(final String imdbid) {
			this.imdbid = imdbid;
		}

		/**
		 * Champ : DATA.
		 * Récupère la valeur de la propriété 'Id Imdb'.
		 * @return String imdbid
		 */
		@Field(domain = "DO_TRANSLATIONS", label = "Id Imdb")
		public Translations getTranslations() {
			return translations;
		}

		public void setTranslations(final Translations translations) {
			this.translations = translations;
		}

	}

	public static class DemoSqlApdapterProviderPlugin implements SqlAdapterSupplierPlugin {

		@Override
		public List<SqlAdapter> getAdapters() {
			return Collections.singletonList(new SqlAdapter<Translations, String>() {

				private final Gson gson = new Gson();

				@Override
				public Translations toJava(final String sqlValue) {
					return gson.fromJson(sqlValue, Translations.class);
				}

				@Override
				public String toSql(final Translations javaValue) {
					return gson.toJson(javaValue);
				}

				@Override
				public Class<Translations> getJavaDataType() {
					return Translations.class;
				}

				@Override
				public Class<String> getSqlDataType() {
					return String.class;
				}

			});
		}

	}

	public class Translations {

		final String french;
		final String spanish;
		final String english;

		Translations(
				final String french,
				final String spanish,
				final String english) {
			this.french = french;
			this.spanish = spanish;
			this.english = english;

		}
	}

}
