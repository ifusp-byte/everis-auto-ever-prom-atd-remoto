package br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.impl;

import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.read;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.writeAsString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.gov.caixa.siavl.atendimentoremoto.dto.CamposDinamicosModeloNotaValidacaoDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.CampoModeloNotaRepositoryImpl;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.CamposDinamicosMascaraValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validator.CamposDinamicosMascaraValidator;

@Service
@SuppressWarnings("all")
public class CamposDinamicosMascaraValidatorServiceImpl implements CamposDinamicosMascaraValidatorService {

	@Autowired
	CampoModeloNotaRepositoryImpl campoModeloNotaRepositoryImpl;

	private final List<CamposDinamicosMascaraValidator> camposDinamicosMascaraValidators;

	public CamposDinamicosMascaraValidatorServiceImpl(
			List<CamposDinamicosMascaraValidator> camposDinamicosMascaraValidators) {
		this.camposDinamicosMascaraValidators = camposDinamicosMascaraValidators;
	}

	@Override
	public List<ViolacaoDto> validateMascara(CriaNotaInputDTO criaNotaInputDto) {

		List<List<ViolacaoDto>> lista = camposDinamicosMascaraValidators.stream()
				.map(validator -> validator.validateMascara(criaNotaInputDto)).filter(Optional::isPresent)
				.map(Optional::get).toList();

		return !lista.isEmpty() ? lista.get(0) : new ArrayList<>();
	}

	@Override
	public List<CamposDinamicosModeloNotaValidacaoDTO> obterIdsValoresDinamicosInput(
			CriaNotaInputDTO criaNotaInputDto) {

		List<CamposDinamicosModeloNotaValidacaoDTO> idsValoresDinamicosInput = new ArrayList<>();
		JsonNode body = read(writeAsString(criaNotaInputDto));
		ArrayNode campos = (ArrayNode) body.get("campos");

		if (!campos.isEmpty()) {
			for (JsonNode node : campos) {
				CamposDinamicosModeloNotaValidacaoDTO dinamicoInput = new CamposDinamicosModeloNotaValidacaoDTO();
				dinamicoInput.setIdCampoDinamico(String.valueOf(node.path("id").asText()).trim());
				dinamicoInput.setValorInput(String.valueOf(node.path("valor").asText()).trim());
				idsValoresDinamicosInput.add(dinamicoInput);
			}

		}

		return idsValoresDinamicosInput;
	}

	@Override
	public List<CamposDinamicosModeloNotaValidacaoDTO> obterIdsTamanhosDinamicos(CriaNotaInputDTO criaNotaInputDto) {
		Long numeroModeloNota = Long.valueOf(String.valueOf(criaNotaInputDto.getIdModeloNota()));
		return campoModeloNotaRepositoryImpl.camposDinamicosValidacaoMascara(numeroModeloNota);
	}

}
