package br.gov.caixa.siavl.atendimentoremoto.util;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.CHANNEL;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.HTTP;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.HTTPS;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.Timeout;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLContexts;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
@ApplicationScope
@SuppressWarnings("all")
public class RestTemplateUtils {

	public RestTemplateDto newRestTemplate() {

		RestTemplateDto restTemplateDto = new RestTemplateDto();
		HttpComponentsClientHttpRequestFactory requestFactory = null;
		SSLContext sslContext = null;
		RestTemplate restTemplate = null;
		CloseableHttpClient httpClient = null;

		try {
			sslContext = SSLContexts.custom().loadTrustMaterial(null, (chain, authType) -> true).build();
			SSLConnectionSocketFactory sSLConnectionFactory = new SSLConnectionSocketFactory(sslContext,
					new String[] { CHANNEL }, null, new NoopHostnameVerifier());
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register(HTTPS, sSLConnectionFactory)
					.register(HTTP, sSLConnectionFactory)					
					.build();
			PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
			connectionManager.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(Timeout.ofSeconds(100000)).build());
			httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();			
			requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);			
			restTemplate = new RestTemplate(requestFactory);
			restTemplateDto.setHttpClient(httpClient);
			restTemplateDto.setRestTemplate(restTemplate);
			return restTemplateDto;
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			throw new RuntimeException(e);
		}
	}

}
