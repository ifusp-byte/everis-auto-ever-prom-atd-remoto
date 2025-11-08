package br.gov.caixa.siavl.atendimentoremoto.controller;

import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.BASE_URL;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.MODELOS;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.MODELO_DETALHES;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.NOTA_CONTRATAR;
import static br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints.NOTA_CRIAR;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.ALL;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.AUTHORIZATION;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.getToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.caixa.siavl.atendimentoremoto.doc.AtendimentoRemotoControllerDoc;
import br.gov.caixa.siavl.atendimentoremoto.dto.ContrataNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.service.ContrataNotaService;
import br.gov.caixa.siavl.atendimentoremoto.service.CriaNotaService;
import br.gov.caixa.siavl.atendimentoremoto.service.ModeloNotaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping(BASE_URL)
@CrossOrigin(origins = ALL)
@SuppressWarnings("all")
public class AtendimentoRemotoController implements AtendimentoRemotoControllerDoc {

	@Autowired
	ContrataNotaService contrataNotaService;

	@Autowired
	ModeloNotaService modeloNotaService;

	@Autowired
	CriaNotaService criaNotaService;

	@Override
	@GetMapping(MODELOS)
	public Object modelos(@RequestParam(name = "idProduto", required = false) Long idProduto) throws Exception {
		return modeloNotaService.modelos(idProduto);
	}

	@GetMapping(MODELO_DETALHES)
	public Object detalhesModelo(@PathVariable(name = "idModelo", required = true) Long idModelo) throws Exception {
		return modeloNotaService.detalhesModelo(idModelo);
	}

	@PostMapping(NOTA_CRIAR)
	public Object criaNota(
			@Valid @NotNull @NotBlank @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @RequestBody CriaNotaInputDTO criaNotaInputDto) throws Exception {
		return criaNotaService.criaNota(criaNotaInputDto, getToken(token));
	}

	@PostMapping(NOTA_CONTRATAR)
	public Object contrataNota(
			@Valid @NotNull @NotBlank @RequestHeader(value = AUTHORIZATION, required = true) String token,
			@Valid @RequestBody ContrataNotaInputDTO contrataNotaInputDto) throws Exception {
		return contrataNotaService.contrataNota(contrataNotaInputDto, getToken(token));
	}

}
