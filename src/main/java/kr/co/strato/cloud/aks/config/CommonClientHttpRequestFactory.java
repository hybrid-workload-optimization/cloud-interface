package kr.co.strato.cloud.aks.config;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.springframework.http.client.SimpleClientHttpRequestFactory;


public class CommonClientHttpRequestFactory extends SimpleClientHttpRequestFactory{
	public static final int HTTP_READ_TIMEOUT = 10*1000;
	public static final int HTTP_CONNECTION_TIMEOUT = 5*1000;
	
	@Override
	protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
		super.prepareConnection(connection, httpMethod);
		connection.setReadTimeout(HTTP_READ_TIMEOUT);
		connection.setConnectTimeout(HTTP_CONNECTION_TIMEOUT);
	}
}

