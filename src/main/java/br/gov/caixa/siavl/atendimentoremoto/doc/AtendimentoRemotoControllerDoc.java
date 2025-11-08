package br.gov.caixa.siavl.atendimentoremoto.doc;

import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.MODELOS;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@SuppressWarnings("all")
@Tag(name = "API para a contratação automatizada de notas de negociação.")
public interface AtendimentoRemotoControllerDoc {

	@Operation(summary = "Obtém todos os modelos de nota de negociação publicados.", responses = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
					@ExampleObject(value = "Modelos carregados com sucesso."),
					@ExampleObject(value = "[\r\n" + "{\r\n" + "\"idModelo\": 8185,\r\n" + "\"idProduto\": 2264,\r\n"
							+ "\"descProduto\": \"COMPULS S/CADERNETA DE POUPANCA - PJ\",\r\n"
							+ "\"automatica\": true\r\n" + "},\r\n" + "{\r\n" + "\"idModelo\": 8343,\r\n"
							+ "\"idProduto\": 1463,\r\n"
							+ "\"descProduto\": \"TITULO DE CAPITALIZACAO CAP GANHADOR PAGAMENTO MENSAL\",\r\n"
							+ "\"automatica\": true\r\n" + "},\r\n" + "{\r\n" + "\"idModelo\": 8344,\r\n"
							+ "\"idProduto\": 2404,\r\n"
							+ "\"descProduto\": \"CARTAO DE CREDITO - ROTATIVO HIBRIDO PF\",\r\n"
							+ "\"automatica\": true\r\n" + "},\r\n" + "{\r\n" + "\"idModelo\": 8345,\r\n"
							+ "\"idProduto\": 2384,\r\n" + "\"descProduto\": \"CAIXACAP MILHAO\",\r\n"
							+ "\"automatica\": true\r\n" + "},\r\n" + "{\r\n" + "\"idModelo\": 8784,\r\n"
							+ "\"idProduto\": 1483,\r\n" + "\"descProduto\": \"CREDITO AUTO CAIXA - PF\",\r\n"
							+ "\"automatica\": true\r\n" + "}\r\n" + "]") }
			)) })
	@GetMapping(MODELOS)
	Object modelos(@RequestParam(name = "idProduto", required = false) Long idProduto) throws Exception;

}
