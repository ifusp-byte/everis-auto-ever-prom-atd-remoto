package br.gov.caixa.siavl.atendimentoremoto.util;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class RestTemplateDto {

	RestTemplate RestTemplate;

	CloseableHttpClient httpClient;

}
