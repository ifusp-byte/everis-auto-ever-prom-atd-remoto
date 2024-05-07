package br.gov.caixa.siavl.atendimentoremoto.util;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
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
@SuppressWarnings({"deprecation", "squid:S1488", "squid:S4507"})
public class RestTemplateUtils {
	
	static Logger logger = Logger.getLogger(RestTemplateUtils.class.getName());

	public RestTemplate newRestTemplate() {

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		SSLContext sslcontext = null;
		RestTemplate restTemplate = null;

		try {
			sslcontext = SSLContexts.custom().loadTrustMaterial(null, (chain, authType) -> true).build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			logger.log(Level.SEVERE, e.getLocalizedMessage());
		} finally {
		SSLConnectionSocketFactory sSlConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1.2" }, null, new NoopHostnameVerifier());
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sSlConnectionSocketFactory).build();
		requestFactory.setHttpClient(httpClient);
		restTemplate = new RestTemplate(requestFactory);
		}

		return restTemplate;

	}

}
