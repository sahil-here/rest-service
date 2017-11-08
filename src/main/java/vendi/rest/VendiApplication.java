package vendi.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.dropwizard.guice.GuiceBundle;

import io.dropwizard.Application;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import vendi.bundle.HttpClientBundle;
import vendi.exception.VendiExceptionMapper;
import vendi.health.VendiHealthCheck;
import vendi.module.VendiModule;
import vendi.rest.dao.entity.FieldExecutive;
import vendi.rest.dao.entity.Shipment;
import vendi.rest.dao.entity.StatusHistory;
import vendi.rest.dao.entity.Vendor;
import vendi.rest.resources.FieldExecutiveResources;
import vendi.rest.resources.LoginResources;
import vendi.rest.resources.RotationResource;
import vendi.rest.resources.ShipmentResources;

public class VendiApplication extends Application<VendiConfiguration> {
	
	protected static final Logger logger = LoggerFactory.getLogger(VendiApplication.class);

	private final HibernateBundle<VendiConfiguration> hibernateBundle = new HibernateBundle<VendiConfiguration>(
			FieldExecutive.class, Shipment.class, StatusHistory.class, Vendor.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(VendiConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	private final HttpClientBundle<VendiConfiguration> httpClientBundle = new HttpClientBundle<VendiConfiguration>() {
		@Override
		public HttpClientConfiguration getHttpConfiguration(VendiConfiguration configuration) {
			return configuration.getHttpClientConfiguration();
		}
	};

	private final SwaggerBundle<VendiConfiguration> swaggerBundle = new SwaggerBundle<VendiConfiguration>() {
		@Override
		protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(VendiConfiguration configuration) {
			return configuration.getSwaggerBundleConfiguration();
		}
	};

	private GuiceBundle<VendiConfiguration> guiceBundle;

	public static void main(String[] args) throws Exception {
		new VendiApplication().run(args);
		logger.info("Vendi Service is up");
	}

	@Override
	public void initialize(Bootstrap<VendiConfiguration> bootstrap) {

		guiceBundle = GuiceBundle.<VendiConfiguration> newBuilder()
				.addModule(getVendiModule(httpClientBundle, hibernateBundle,bootstrap.getObjectMapper()))
				.setConfigClass(VendiConfiguration.class).build();

		bootstrap.addBundle(hibernateBundle);
		bootstrap.addBundle(httpClientBundle);
		bootstrap.addBundle(swaggerBundle);
		bootstrap.addBundle(guiceBundle);
	}

	protected VendiModule getVendiModule(HttpClientBundle<VendiConfiguration> httpClientBundle,
										 HibernateBundle<VendiConfiguration> hibernateBundle, ObjectMapper objectMapper) {
		return new VendiModule(httpClientBundle, hibernateBundle, objectMapper);
	}

	@Override
	public void run(VendiConfiguration configuration, Environment environment) throws Exception {
		VendiHealthCheck healthCheck = guiceBundle.getInjector().getInstance(VendiHealthCheck.class);
        environment.healthChecks().register("rotation-health", healthCheck);
		environment.jersey().register(guiceBundle.getInjector().getInstance(LoginResources.class));
		environment.jersey().register(guiceBundle.getInjector().getInstance(FieldExecutiveResources.class));
		environment.jersey().register(guiceBundle.getInjector().getInstance(ShipmentResources.class));
		environment.jersey().register(guiceBundle.getInjector().getInstance(RotationResource.class));
		environment.jersey().register(guiceBundle.getInjector().getInstance(VendiExceptionMapper.class));
	}

}
