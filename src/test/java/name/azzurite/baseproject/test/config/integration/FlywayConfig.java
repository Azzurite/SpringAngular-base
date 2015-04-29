package name.azzurite.baseproject.test.config.integration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(FlywayProperties.class)
public class FlywayConfig {

	@Inject
	private FlywayProperties flywayProperties;

	@Bean
	public FlywayMigrationStrategy flywayMigrationStrategy() {
		return new FlywayMigrationStrategy() {
			@Override
			public void migrate(Flyway flyway) {
				flyway.clean();
				flyway.migrate();
			}
		};
	}


	@Bean
	public Flyway flyway(DataSource dataSource) {
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.setLocations((flywayProperties.getLocations().stream().toArray(String[]::new)));
		return flyway;
	}

	@Bean
	public FlywayMigrationInitializer flywayInitializer(Flyway flyway, FlywayMigrationStrategy migrationStrategy) {
		return new FlywayMigrationInitializer(flyway, migrationStrategy);

	}


	/**
	 * {@link InitializingBean} used to trigger {@link Flyway} migration via the {@link FlywayMigrationStrategy}.
	 */
	private static class FlywayMigrationInitializer implements InitializingBean {

		private final Flyway flyway;

		private final FlywayMigrationStrategy migrationStrategy;

		public FlywayMigrationInitializer(Flyway flyway, FlywayMigrationStrategy migrationStrategy) {
			this.flyway = flyway;
			this.migrationStrategy = migrationStrategy;
		}

		@Override
		public void afterPropertiesSet() throws Exception {
			this.migrationStrategy.migrate(this.flyway);
		}

	}
}
