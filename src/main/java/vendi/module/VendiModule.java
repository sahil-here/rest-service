package vendi.module;

import org.apache.http.client.HttpClient;
import org.hibernate.SessionFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import io.dropwizard.hibernate.HibernateBundle;
import vendi.bundle.HttpClientBundle;
import vendi.rest.VendiConfiguration;
import vendi.rest.config.UrlConfiguration;
import vendi.rest.dao.*;
import vendi.rest.resources.manager.FieldExecutiveManager;
import vendi.rest.resources.manager.IFieldExecutiveManager;
import vendi.rest.resources.manager.ILoginManager;
import vendi.rest.resources.manager.IShipmentManager;
import vendi.rest.resources.manager.LoginManager;
import vendi.rest.resources.manager.ShipmentManager;
import vendi.rest.service.ClientService;
import vendi.rotation.RotationHelper;

public class VendiModule extends AbstractModule {

	private ObjectMapper objectMapper;
	private HttpClientBundle httpClientBundle;
	private HibernateBundle<VendiConfiguration> hibernateBundle;

	public VendiModule(HttpClientBundle httpClientBundle, HibernateBundle<VendiConfiguration> hibernateBundle, ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		this.httpClientBundle = httpClientBundle;
		this.hibernateBundle = hibernateBundle;
	}

	@Override
	protected void configure() {
		bind(ObjectMapper.class).toInstance(objectMapper);
		bind(RotationHelper.class).toInstance(new RotationHelper());
		bind(ClientService.class);
		bind(IFieldExecutiveDAO.class).to(FieldExecutiveDAO.class);
		bind(IShipmentDAO.class).to(ShipmentDAO.class);
		bind(IStatusDAO.class).to(StatusDAO.class);
		bind(IVendorDAO.class).to(VendorDAO.class);
		bind(ILoginManager.class).to(LoginManager.class);
		bind(IFieldExecutiveManager.class).to(FieldExecutiveManager.class);
		bind(IShipmentManager.class).to(ShipmentManager.class);
	}
	
	@Provides
	public UrlConfiguration getUrlConfiguration(VendiConfiguration vendiConfiguration) {
		return vendiConfiguration.getUrlConfiguration();
	}

	@Provides
	public HttpClient getHttpClient() {
		return httpClientBundle.getHttpClient();
	}

	@Provides
	public SessionFactory getSessionFactory() {
		return hibernateBundle.getSessionFactory();
	}

}
