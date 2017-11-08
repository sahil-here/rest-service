package vendi.rest.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import vendi.exception.VendiException;

public abstract class RestClient {

    private HttpClient httpClient;
    private String baseURL;

    public RestClient(HttpClient httpClient, String baseURL) {
        this.httpClient = httpClient;
        this.baseURL = baseURL;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public abstract Logger getLogger();

    public abstract String getClientName();

    protected String doGet(String api, Map<String, String> headers) throws VendiException {
        URI uri = createUri(api);
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeaders(getHeaders(headers));
        getLogger().info("Making " + httpGet.getMethod() + " Call: API=>" + uri + ", Headers=>" + headers);
        return execute(httpGet);
    }

    protected String doPost(String api, String payload, Map<String, String> headers) throws VendiException {
        URI uri = createUri(api);
        HttpPost httpPost = new HttpPost(uri);
        try {
            httpPost.setEntity(new StringEntity(payload));
        } catch (UnsupportedEncodingException e) {
            throw new VendiException(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        httpPost.setHeaders(getHeaders(headers));
        getLogger().info("Making " + httpPost.getMethod() + " Call: API=>" + uri + ", Payload=>" + payload + ", Headers=>" + headers);
        return execute(httpPost);
    }

    private Header[] getHeaders(Map<String, String> headers) {
        List<Header> headerList = new ArrayList<>();
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                headerList.add(new BasicHeader(key, headers.get(key)));
            }
        }
        return headerList.toArray(new Header[headerList.size()]);
    }

    private String execute(HttpUriRequest httpUriRequest) throws VendiException {
        try {
            return call(httpUriRequest);
        } catch (IOException e) {
            throw new VendiException(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage(), e);
        } catch (VendiException e) {
            throw e;
        }
    }

    private String call(HttpUriRequest httpUriRequest) throws IOException, VendiException {
        HttpResponse httpResponse = httpClient.execute(httpUriRequest);
        int responseCode = httpResponse.getStatusLine().getStatusCode();
        getLogger().info("ResponseCode: " + responseCode);
        if (responseCode != HttpStatus.SC_OK) {
            getLogger().error("Call failed with status code : " + responseCode +
                    " and status message : " + httpResponse.getStatusLine().getReasonPhrase());
            getLogger().info("Response : " + EntityUtils.toString(httpResponse.getEntity()));
            throw new VendiException(responseCode, httpResponse.getStatusLine().getReasonPhrase());
        }

        HttpEntity httpEntity = httpResponse.getEntity();
        String responseBody = EntityUtils.toString(httpEntity);
        getLogger().info("ResponseBody: " + responseBody);

        return responseBody;
    }

    private URI createUri(String api) {
        return URI.create(String.format("%s%s", baseURL, api));
    }
}