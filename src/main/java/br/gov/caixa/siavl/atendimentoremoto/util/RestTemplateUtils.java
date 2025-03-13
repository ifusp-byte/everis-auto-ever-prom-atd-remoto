package br.gov.caixa.siavl.atendimentoremoto.util;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.CHANNEL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.util.Timeout;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
@ApplicationScope
@SuppressWarnings("all")
public class RestTemplateUtils {

	public RestTemplateDto newRestTemplate() throws RuntimeException {
		RestTemplateDto restTemplateDto = new RestTemplateDto();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		CloseableHttpClient httpClient = null;
		RestTemplate restTemplate = null;

		try {

			SSLContext sslContext = SSLContextBuilder.create().setProtocol(CHANNEL).build();

			PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

			SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(Timeout.ofSeconds(30)).build();

			connectionManager.setDefaultSocketConfig(socketConfig);

			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(Timeout.ofSeconds(30))
					.setConnectTimeout(Timeout.ofSeconds(10)).build();

			SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
					NoopHostnameVerifier.INSTANCE);

			httpClient = HttpClients.custom().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(requestConfig).build();

			requestFactory.setHttpClient(httpClient);
			restTemplate = new RestTemplate(requestFactory);

			restTemplateDto = RestTemplateDto.builder().httpClient(httpClient).restTemplate(restTemplate).build();

			return restTemplateDto;

		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			throw new RuntimeException("Erro. " + CHANNEL, e);
		}
	}
}
