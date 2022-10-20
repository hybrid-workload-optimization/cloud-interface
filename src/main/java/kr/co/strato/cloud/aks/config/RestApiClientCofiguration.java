package kr.co.strato.cloud.aks.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;





@Configuration
public class RestApiClientCofiguration {
	public static final int HTTP_READ_TIMEOUT = 10*1000;
	public static final int HTTP_CONNECTION_TIMEOUT = 5*1000;
	
	
	private RestTemplate restService;
	private RestTemplate restComponentClientService;	
	private RestTemplate noopHostNameRestService;	
	private RestTemplate noop401HostNameRestService;
	

	
	public RestApiClientCofiguration() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {

			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		};
		
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null,acceptingTrustStrategy).build();
		HttpComponentsClientHttpRequestFactory requestFoctory = new HttpComponentsClientHttpRequestFactory(HttpClientBuilder
												.create()
												.setSSLContext(sslContext/*SSLContexts.custom().loadTrustMaterial(null,acceptingTrustStrategy).build()*/)
												.build());
		
		
		requestFoctory.setConnectTimeout(HTTP_CONNECTION_TIMEOUT);
		requestFoctory.setReadTimeout(HTTP_READ_TIMEOUT);
		

		
		
		restComponentClientService = new RestTemplate();		
		restComponentClientService.getMessageConverters().add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));			
		restComponentClientService.setRequestFactory(requestFoctory);
		
		restService = new RestTemplate(new CommonClientHttpRequestFactory());
		restService.getMessageConverters().add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));

		
	}
		

	@Bean(name="restService")
	public RestTemplate getRestService() {
				
		return restService;
	}


	@Bean(name="restComponentClientService")
	public RestTemplate getRestComponentClientService() {
		return restComponentClientService;
	}
	
	@Bean(name="noopHostNameRestService")
	public RestTemplate getNoopHostnameRestService() throws Exception, NoSuchAlgorithmException, KeyStoreException {
		
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {

			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}		
		};
		
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null,acceptingTrustStrategy).build();
		
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		
		noopHostNameRestService = new RestTemplate(requestFactory);
		return noopHostNameRestService;
	}
	
	@Bean(name="noop401HostNameRestService")
	public RestTemplate getNoop401HostnameRestService() throws Exception, NoSuchAlgorithmException, KeyStoreException {
		
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {

			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}			
		
			
		};
		
		SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null,acceptingTrustStrategy).build();
		
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		
		//noop401HostNameRestService = new RestTemplate(requestFactory);	
		noop401HostNameRestService = new RestTemplate();
		noop401HostNameRestService.setRequestFactory(requestFactory);
		
		noop401HostNameRestService.setErrorHandler(new DefaultResponseErrorHandler() {
			@SuppressWarnings("unused")			
			public boolean hasError(ClientHttpResponse response) throws IOException {
				HttpStatus statusCode = response.getStatusCode();
				return statusCode.series() == HttpStatus.Series.SERVER_ERROR;
				//return statusCode.series() == HttpStatus.Series.CLIENT_ERROR;
			}						
		});
		return noop401HostNameRestService;				
	}

	
	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
		loggingFilter.setIncludeClientInfo(true);
		loggingFilter.setIncludeQueryString(true);
		loggingFilter.setIncludePayload(true);
		loggingFilter.setMaxPayloadLength(64000);
		return loggingFilter;
	}
	

	

}
