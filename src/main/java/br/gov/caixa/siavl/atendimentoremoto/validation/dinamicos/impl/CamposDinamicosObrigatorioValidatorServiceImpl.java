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
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.CamposDinamicosObrigatorioValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validator.CamposDinamicosObrigatorioValidator;

@Service
@SuppressWarnings("all")
public class CamposDinamicosObrigatorioValidatorServiceImpl implements CamposDinamicosObrigatorioValidatorService {

	@Autowired
	CampoModeloNotaRepositoryImpl campoModeloNotaRepositoryImpl;

	private final List<CamposDinamicosObrigatorioValidator> camposDinamicosObrigatorioValidators;

	public CamposDinamicosObrigatorioValidatorServiceImpl(
			List<CamposDinamicosObrigatorioValidator> camposDinamicosObrigatorioValidators) {
		this.camposDinamicosObrigatorioValidators = camposDinamicosObrigatorioValidators;
	}

	@Override
	public List<ViolacaoDto> validateObrigatorio(CriaNotaInputDTO criaNotaInputDto) {

		List<List<ViolacaoDto>> lista = camposDinamicosObrigatorioValidators.stream()
				.map(validator -> validator.validateObrigatorio(criaNotaInputDto)).filter(Optional::isPresent)
				.map(Optional::get).toList();

		return !lista.isEmpty() ? lista.get(0) : new ArrayList<>();
	}

	@Override
	public String[] obterIdsCamposDinamicosInput(CriaNotaInputDTO criaNotaInputDto) {

		List<String> idsCamposDinamicosInput = new ArrayList<>();
		JsonNode body = read(writeAsString(criaNotaInputDto));
		ArrayNode campos = (ArrayNode) body.get("campos");

		if (!campos.isEmpty()) {
			for (JsonNode node : campos) {
				idsCamposDinamicosInput.add(String.valueOf(node.path("id").asText()).trim());
			}

		}

		return idsCamposDinamicosInput.toArray(String[]::new);

	}

	@Override
	public List<CamposDinamicosModeloNotaValidacaoDTO> valoresDinamicosInput(CriaNotaInputDTO criaNotaInputDto) {

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
	public List<Long> camposDinamicosIdsObrigatorios(CriaNotaInputDTO criaNotaInputDto) {
		Long numeroModeloNota = Long.valueOf(String.valueOf(criaNotaInputDto.getIdModeloNota()));
		return campoModeloNotaRepositoryImpl.camposDinamicosValidacaoObrigatorio(numeroModeloNota);
	}

}
