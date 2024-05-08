package br.gov.caixa.siavl.atendimentoremoto.util;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
@ApplicationScope
@SuppressWarnings({ "deprecation", "squid:S1488", "squid:S4507" })
public class RestTemplateUtils {

	private final static Logger LOG = Logger.getLogger(RestTemplateUtils.class.getName());

	public RestTemplate newRestTemplate() {

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		SSLContext sslcontext = null;
		RestTemplate restTemplate = null;
		CloseableHttpClient httpClient = null;

		try {
			sslcontext = SSLContexts.custom().loadTrustMaterial(null, (chain, authType) -> true).build();
			SSLConnectionSocketFactory sSlConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext,
					new String[] { "TLSv1.2" }, null, new NoopHostnameVerifier());
			httpClient = HttpClients.custom().setSSLSocketFactory(sSlConnectionSocketFactory).build();
			requestFactory.setHttpClient(httpClient);
			restTemplate = new RestTemplate(requestFactory);
			return restTemplate;
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			throw new RuntimeException(e);
		}
	}
}
