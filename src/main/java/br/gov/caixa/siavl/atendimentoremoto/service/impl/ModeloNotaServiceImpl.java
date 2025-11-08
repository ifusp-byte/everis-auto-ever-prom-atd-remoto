package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.constants.ModelosNotaConstants;
import br.gov.caixa.siavl.atendimentoremoto.dto.CampoOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.ErroOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.ModeloOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.ProdutoNotaDto;
import br.gov.caixa.siavl.atendimentoremoto.repository.CampoModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.ModeloNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.RoteiroFechamentoNotaRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.ModeloNotaService;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings("all")
public class ModeloNotaServiceImpl implements ModeloNotaService {

	@Autowired
	MetodosUtils metodosUtils;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	ModeloNotaRepository modeloNotaRepository;

	@Autowired
	CampoModeloNotaRepository campoModeloNotaRepository;

	@Autowired
	RoteiroFechamentoNotaRepository roteiroFechamentoNotaRepository;

	public Object modelos(Long idAcaoProduto) throws Exception{

		List<ModeloOutputDto> modelosNota = new ArrayList<>();

		if (idAcaoProduto == null) {

			modeloNotaRepository.modelos().stream().forEach(modeloNota ->

			{

				ModeloOutputDto modeloOutputDto = null;

				modeloOutputDto = ModeloOutputDto.builder().idModelo(Long.parseLong(String.valueOf(modeloNota[0])))
						.isAutomatica(Boolean.parseBoolean(String.valueOf(modeloNota[1])
								.equalsIgnoreCase(ModelosNotaConstants.CONTRATACAO_AUTOMATICA_S) ? "true" : "false"))
						.idProduto(Long.parseLong(String.valueOf(modeloNota[2])))
						.descProduto(String.valueOf(modeloNota[3])).build();

				modelosNota.add(modeloOutputDto);

			});

		} else {

			modeloNotaRepository.modelosByProduto(idAcaoProduto).stream().forEach(modeloNota ->

			{

				ModeloOutputDto modeloOutputDto = null;

				modeloOutputDto = ModeloOutputDto.builder().idModelo(Long.parseLong(String.valueOf(modeloNota[0])))
						.isAutomatica(Boolean.parseBoolean(String.valueOf(modeloNota[1])
								.equalsIgnoreCase(ModelosNotaConstants.CONTRATACAO_AUTOMATICA_S) ? "true" : "false"))
						.idProduto(Long.parseLong(String.valueOf(modeloNota[2])))
						.descProduto(String.valueOf(modeloNota[3])).build();

				modelosNota.add(modeloOutputDto);

			});

		}

		ResponseEntity<Object> responseEntity = null;
		ErroOutputDto erroType = null;

		if (modelosNota.isEmpty()) {
			erroType = ErroOutputDto.builder().codErro(HttpStatus.NO_CONTENT.value())
					.descricao(ModelosNotaConstants.NO_CONTENT).build();
			responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).body(erroType);
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(modelosNota);
		}

		return responseEntity;

	}

	public Object detalhesModelo(Long idModelo) throws Exception {

		List<ProdutoNotaDto> produtoNotaLista = new ArrayList<>();
		List<CampoOutputDto> dinamicos = new ArrayList<>();
		ModeloDTO modeloComCamposOutputDto = new ModeloDTO();

		modeloNotaRepository.notaProduto(idModelo).stream().forEach(notaProduto -> {
			ProdutoNotaDto notaProdutoItem = null;
			notaProdutoItem = ProdutoNotaDto.builder().tipoNota(String.valueOf(notaProduto[0]).trim())
					.descProduto(String.valueOf(notaProduto[1]).trim())
					.idProduto(Long.parseLong(String.valueOf(notaProduto[2]))).build();
			produtoNotaLista.add(notaProdutoItem);
		});

		modeloNotaRepository.modeloNotaDinamico(idModelo).stream().forEach(dinamico -> {

			CampoOutputDto modeloDinamico = null;
			modeloDinamico = CampoOutputDto.builder()
					.idModelo(Long.parseLong(String.valueOf(dinamico[0])))
					.idCampo(Long.parseLong(String.valueOf(dinamico[1])))
					.nome(String.valueOf(dinamico[3]))
					.isEditavel(String.valueOf(dinamico[5]).equals("1"))
					.isObrigatorio(String.valueOf(dinamico[6]).equals("1"))
					.tipoDado(String.valueOf(dinamico[8]))
					.qtdCaracteres(String.valueOf(dinamico[11]))
					.build();
			dinamicos.add(modeloDinamico);
		});

		if (!produtoNotaLista.isEmpty()) {
			modeloComCamposOutputDto.setIdModelo(idModelo);
			modeloComCamposOutputDto.setIdProduto(produtoNotaLista.get(0).getIdProduto());
			modeloComCamposOutputDto.setDescProduto(produtoNotaLista.get(0).getDescProduto());
			modeloComCamposOutputDto.setTipoNota(produtoNotaLista.get(0).getTipoNota());
			modeloComCamposOutputDto.setCampos(metodosUtils.writeValueAsString(dinamicos));
		}

		ResponseEntity<Object> responseEntity = null;
		ErroOutputDto erroOutputDto = null;

		if (dinamicos.isEmpty()) {
			erroOutputDto = ErroOutputDto.builder().codErro(HttpStatus.NO_CONTENT.value())
					.descricao(ModelosNotaConstants.NO_CONTENT).build();
			responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).body(erroOutputDto);
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(modeloComCamposOutputDto);
		}

		return responseEntity;

	}

	public List<CampoOutputDto> camposDinamicosModeloNota(Long idModeloNota) {

		List<CampoOutputDto> dinamicos = new ArrayList<>();

		modeloNotaRepository.modeloNotaDinamico(idModeloNota).stream().forEach(dinamico -> {

			CampoOutputDto modeloDinamico = null;
			modeloDinamico = CampoOutputDto.builder()
					.idModelo(Long.parseLong(String.valueOf(dinamico[0])))
					.idCampo(Long.parseLong(String.valueOf(dinamico[1])))
					.nome(String.valueOf(dinamico[3]))
					.isEditavel(String.valueOf(dinamico[5]).equals("1"))
					.isObrigatorio(String.valueOf(dinamico[6]).equals("1"))
					.tipoDado(String.valueOf(dinamico[8]))
					.qtdCaracteres(String.valueOf(dinamico[11]))
					.build();
			dinamicos.add(modeloDinamico);
		});

		return dinamicos;

	}

}
