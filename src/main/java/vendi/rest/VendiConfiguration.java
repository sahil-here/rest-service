package vendi.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import vendi.rest.config.UrlConfiguration;

public class VendiConfiguration extends Configuration{
	
	@JsonProperty("swagger")
    private SwaggerBundleConfiguration swaggerBundleConfiguration;
	
	@Valid
	@NotNull
	@JsonProperty("httpClient")
	private HttpClientConfiguration httpClient = new HttpClientConfiguration();
	
	@Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();
	
	@Valid
	@NotNull
	@JsonProperty("urlConfiguration")
	private UrlConfiguration urlConfiguration = new UrlConfiguration();
	
	public DataSourceFactory getDataSourceFactory() {
        return database;
    }
	
	public void setDataSourceFactory(DataSourceFactory database){
		this.database = database;
	}
	
	public HttpClientConfiguration getHttpClientConfiguration() {
		return httpClient;
	}
	
	public UrlConfiguration getUrlConfiguration() {
		return urlConfiguration;
	}
	
	public void setUrlConfiguration(UrlConfiguration urlConfiguration) {
		this.urlConfiguration = urlConfiguration;
	}
	
	public SwaggerBundleConfiguration getSwaggerBundleConfiguration() {
		return swaggerBundleConfiguration;
	}
	
	public void setSwaggerBundleConfiguration(SwaggerBundleConfiguration swaggerBundleConfiguration) {
		this.swaggerBundleConfiguration = swaggerBundleConfiguration;
	}
	
}
