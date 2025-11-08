package com.empresa.immediate.service.impl;

import org.springframework.stereotype.Service;

import com.empresa.immediate.dto.ViolacaoDTO;
import com.empresa.immediate.service.ImmediateCreateValidatorService;
import com.empresa.immediate.util.constant.ImmediateCreateConstant;

@Service
public class ImmediateCreateValidatorServiceImpl implements ImmediateCreateValidatorService {

	public ViolacaoDTO violacaoBuild(ViolacaoDTO violacaoRequestDTO, String razao, String propriedade) {

		violacaoRequestDTO.setRazao(razao);
		violacaoRequestDTO.setPropriedade(propriedade);

		return violacaoRequestDTO;

	}

	@Override
	public ViolacaoDTO txidArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_TXID,
				ImmediateCreateConstant.MSG_PROPRIEDADE_TXID);
	}

	@Override
	public ViolacaoDTO calendarioexpiracaoArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_CALENDARIO_EXPIRACAO,
				ImmediateCreateConstant.MSG_PROPRIEDADE_CALENDARIO_EXPIRACAO);
	}

	@Override
	public ViolacaoDTO devedorcpfArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_DEVEDOR,
				ImmediateCreateConstant.MSG_PROPRIEDADE_DEVEDOR);
	}

	@Override
	public ViolacaoDTO devedorcnpjArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_DEVEDOR,
				ImmediateCreateConstant.MSG_PROPRIEDADE_DEVEDOR);
	}

	@Override
	public ViolacaoDTO devedornomeArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_DEVEDOR,
				ImmediateCreateConstant.MSG_PROPRIEDADE_DEVEDOR);
	}

	@Override
	public ViolacaoDTO locidArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_LOC_ID,
				ImmediateCreateConstant.MSG_PROPRIEDADE_LOC_ID);
	}

	@Override
	public ViolacaoDTO valororiginalArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_VALOR_ORIGINAL,
				ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_ORIGINAL_1);
	}

	@Override
	public ViolacaoDTO valormodalidadealteracaoArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_VALOR_MODALIDADEALTERACAO,
				ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_MODALIDADEALTERACAO);
	}

	@Override
	public ViolacaoDTO chaveArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_CHAVE1,
				ImmediateCreateConstant.MSG_PROPRIEDADE_CHAVE);
	}

	@Override
	public ViolacaoDTO solicitacaopagadorArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_SOLICITACAOPAGADOR,
				ImmediateCreateConstant.MSG_PROPRIEDADE_SOLICITACAOPAGADOR);
	}

	@Override
	public ViolacaoDTO infoadicionaisArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_INFOADICIONAIS,
				ImmediateCreateConstant.MSG_PROPRIEDADE_INFOADICIONAIS);
	}

	@Override
	public ViolacaoDTO valorretiradasaquevalorArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_VALOR_RETIRADA_SAQUE,
				ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_SAQUE_VALOR);
	}

	@Override
	public ViolacaoDTO valorretiradasaquemodalidadealteracaoArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_VALOR_RETIRADA_SAQUE,
				ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_SAQUE_MODALIDADE_ALTERACAO);
	}

	@Override
	public ViolacaoDTO valorretiradasaquemodalidadeagenteArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_VALOR_RETIRADA_SAQUE,
				ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_SAQUE_MODALIDADE_AGENTE);
	}

	@Override
	public ViolacaoDTO valorretiradasaqueprestadordoservicodesaqueArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_VALOR_RETIRADA_SAQUE,
				ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_SAQUE_PRESTADOR_SERVICO_SAQUE);
	}

	@Override
	public ViolacaoDTO valorretiradatrocovalorArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_VALOR_RETIRADA_TROCO,
				ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_TROCO_VALOR);
	}

	@Override
	public ViolacaoDTO valorretiradatrocomodalidadealteracaoArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_VALOR_RETIRADA_TROCO,
				ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_TROCO_MODALIDADE_ALTERACAO);
	}

	@Override
	public ViolacaoDTO valorretiradatrocomodalidadeagenteArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_VALOR_RETIRADA_TROCO,
				ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_TROCO_MODALIDADE_AGENTE);
	}

	@Override
	public ViolacaoDTO valorretiradatrocoprestadordoservicodesaqueArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_VALOR_RETIRADA_TROCO,
				ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_TROCO_PRESTADOR_SERVICO_SAQUE);
	}

	@Override
	public ViolacaoDTO localbranchArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_CHAVE2,
				ImmediateCreateConstant.MSG_PROPRIEDADE_CHAVE);
	}

	@Override
	public ViolacaoDTO accountnumberArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_CHAVE2,
				ImmediateCreateConstant.MSG_PROPRIEDADE_CHAVE);
	}

	@Override
	public ViolacaoDTO taxidArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_CHAVE2,
				ImmediateCreateConstant.MSG_PROPRIEDADE_CHAVE);
	}

	@Override
	public ViolacaoDTO aliasstatusArgumentValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_CHAVE3,
				ImmediateCreateConstant.MSG_PROPRIEDADE_CHAVE);
	}

	@Override
	public ViolacaoDTO customerInformationValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_CHAVE4,
				ImmediateCreateConstant.MSG_PROPRIEDADE_CHAVE);
	}

	@Override
	public ViolacaoDTO customerAuthorizationValidation() {

		return violacaoBuild(new ViolacaoDTO(), ImmediateCreateConstant.MSG_RAZAO_CADASTRO,
				ImmediateCreateConstant.MSG_PROPRIEDADE_CADASTRO);
	}

}
