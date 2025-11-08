package br.tradingbot.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BinanceService {

	private final HttpClient http = HttpClient.newHttpClient();
	private final ObjectMapper mapper = new ObjectMapper();

	private final String apiKey;
	private final String secretKey;
	private final String baseUrl;
	private final long recvWindow = 5_000;

	// LOT_SIZE filters
	private BigDecimal minQty;
	private BigDecimal stepSize;

	// para sincronizar horário
	private long timeOffset;

	public BinanceService(@Value("${binance.api.key}") String apiKey, @Value("${binance.api.secret}") String secretKey,
			@Value("${binance.api.base-url}") String baseUrl) {
		this.apiKey = apiKey;
		this.secretKey = secretKey;
		this.baseUrl = baseUrl;

		// 1) sincroniza relógio
		long serverTime = fetchServerTime();
		this.timeOffset = serverTime - System.currentTimeMillis();
		System.out.println("Binance time offset = " + timeOffset + " ms");

		// 2) busca filtros LOT_SIZE para o par
		fetchLotSizeFilters("BTCUSDT");
		System.out.println("minQty = " + minQty + ", stepSize = " + stepSize);
	}

	private long fetchServerTime() {
		try {
			String url = baseUrl + "/api/v3/time";
			HttpRequest r = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
			String body = http.send(r, HttpResponse.BodyHandlers.ofString()).body();
			JsonNode node = mapper.readTree(body);
			return node.get("serverTime").asLong();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar serverTime", e);
		}
	}

	private void fetchLotSizeFilters(String symbol) {
		try {
			String url = baseUrl + "/api/v3/exchangeInfo?symbol=" + symbol;
			HttpRequest r = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
			String body = http.send(r, HttpResponse.BodyHandlers.ofString()).body();
			JsonNode filters = mapper.readTree(body).get("symbols").get(0).get("filters");
			for (JsonNode f : filters) {
				if ("LOT_SIZE".equals(f.get("filterType").asText())) {
					minQty = new BigDecimal(f.get("minQty").asText());
					stepSize = new BigDecimal(f.get("stepSize").asText());
					return;
				}
			}
			throw new IllegalStateException("Filtro LOT_SIZE não encontrado");
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar filtros LOT_SIZE", e);
		}
	}

	/**
	 * Ajusta a quantidade para obedecer ao stepSize e ao minQty. Se for menor que
	 * minQty, retorna ZERO.
	 */
	public BigDecimal ajustarQuantidade(BigDecimal rawQty) {
		// quantas “unidades” de step cabem em rawQty:
		BigDecimal steps = rawQty.divide(stepSize, 0, RoundingMode.DOWN);
		BigDecimal adj = steps.multiply(stepSize);
		if (adj.compareTo(minQty) < 0) {
			return BigDecimal.ZERO;
		}
		return adj;
	}

	public BigDecimal getPrecoAtual(String symbol) {
		try {
			String url = String.format("%s/api/v3/ticker/price?symbol=%s", baseUrl, symbol);
			HttpRequest r = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
			String body = http.send(r, HttpResponse.BodyHandlers.ofString()).body();
			return new BigDecimal(mapper.readTree(body).get("price").asText());
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar preço", e);
		}
	}

	public void colocarOrdem(String symbol, String side, BigDecimal rawQuantity, BigDecimal price) {
		// 3) ajusta quantidade
		BigDecimal quantity = ajustarQuantidade(rawQuantity);
		if (quantity.compareTo(BigDecimal.ZERO) == 0) {
			System.out.println("Quantidade ajustada abaixo do mínimo (" + minQty + "). Ordem ignorada.");
			return;
		}

		try {
			long timestamp = System.currentTimeMillis() + timeOffset;
			String qs = "symbol=" + symbol + "&side=" + side + "&type=LIMIT" + "&timeInForce=GTC" + "&quantity="
					+ quantity.toPlainString() + "&price=" + price.toPlainString() + "&recvWindow=" + recvWindow
					+ "&timestamp=" + timestamp;

			String signature = hmacSha256(qs);
			String url = baseUrl + "/api/v3/order?" + qs + "&signature=" + signature;

			HttpRequest r = HttpRequest.newBuilder().uri(URI.create(url)).header("X-MBX-APIKEY", apiKey)
					.POST(HttpRequest.BodyPublishers.noBody()).build();

			HttpResponse<String> resp = http.send(r, HttpResponse.BodyHandlers.ofString());
			if (resp.statusCode() != 200) {
				throw new RuntimeException("Erro ao enviar ordem: HTTP " + resp.statusCode() + " / " + resp.body());
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao enviar ordem", e);
		}
	}

	private String hmacSha256(String data) {
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
			byte[] raw = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return HexFormat.of().formatHex(raw);
		} catch (Exception e) {
			throw new RuntimeException("Falha no HMACSHA256", e);
		}
	}
}
