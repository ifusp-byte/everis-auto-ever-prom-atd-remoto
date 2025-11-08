package br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.impl;

import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.read;
import static br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils.writeAsString;
import static br.gov.caixa.siavl.atendimentoremoto.util.ViolacoeUtils.violacaoLista;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ViolacaoDto;
import br.gov.caixa.siavl.atendimentoremoto.repository.impl.CampoModeloNotaRepositoryImpl;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.CamposDinamicosFiltroValidatorService;
import br.gov.caixa.siavl.atendimentoremoto.validation.dinamicos.validator.CamposDinamicosFiltroValidator;

@Service
@SuppressWarnings("all")
public class CamposDinamicosFiltroValidatorServiceImpl implements CamposDinamicosFiltroValidatorService {

	@Autowired
	CampoModeloNotaRepositoryImpl campoModeloNotaRepositoryImpl;

	private final List<CamposDinamicosFiltroValidator> camposDinamicosFiltroValidators;

	public CamposDinamicosFiltroValidatorServiceImpl(
			List<CamposDinamicosFiltroValidator> camposDinamicosFiltroValidators) {
		this.camposDinamicosFiltroValidators = camposDinamicosFiltroValidators;
	}

	@Override
	public List<ViolacaoDto> validateFiltro(CriaNotaInputDTO criaNotaInputDto) {
		List<List<ViolacaoDto>> lista = camposDinamicosFiltroValidators.stream()
				.map(validator -> validator.validateFiltro(criaNotaInputDto)).filter(Optional::isPresent)
				.map(Optional::get).toList();
		return violacaoLista(lista);
	}

	@Override
	public List<String> obterIdsCamposDinamicosInput(CriaNotaInputDTO criaNotaInputDto) {

		List<String> idsCamposDinamicosInput = new ArrayList<>();
		JsonNode body = read(writeAsString(criaNotaInputDto));
		ArrayNode campos = null;

		if (criaNotaInputDto.getCampos() != null) {
			campos = (ArrayNode) body.get("campos");
		}

		if (campos != null && !campos.isEmpty()) {
			for (JsonNode node : campos) {
				idsCamposDinamicosInput.add(String.valueOf(node.path("id").asText()).trim());
			}

		}

		return idsCamposDinamicosInput;

	}

	@Override
	public Long[] camposDinamicosIds(CriaNotaInputDTO criaNotaInputDto) {
		Long numeroModeloNota = Long.valueOf(String.valueOf(criaNotaInputDto.getIdModeloNota()));
		return campoModeloNotaRepositoryImpl.camposDinamicosValidacaoFiltro(numeroModeloNota).toArray(Long[]::new);
	}

}
