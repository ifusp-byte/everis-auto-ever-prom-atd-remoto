package com.empresa.immediate.service;

import com.empresa.immediate.dto.ViolacaoDTO;

public interface ImmediateCreateValidatorService {

	ViolacaoDTO txidArgumentValidation();

	ViolacaoDTO calendarioexpiracaoArgumentValidation();

	ViolacaoDTO devedorcpfArgumentValidation();

	ViolacaoDTO devedorcnpjArgumentValidation();

	ViolacaoDTO devedornomeArgumentValidation();

	ViolacaoDTO locidArgumentValidation();

	ViolacaoDTO valororiginalArgumentValidation();

	ViolacaoDTO valormodalidadealteracaoArgumentValidation();

	ViolacaoDTO chaveArgumentValidation();

	ViolacaoDTO solicitacaopagadorArgumentValidation();

	ViolacaoDTO infoadicionaisArgumentValidation();

	ViolacaoDTO valorretiradasaquevalorArgumentValidation();

	ViolacaoDTO valorretiradasaquemodalidadealteracaoArgumentValidation();

	ViolacaoDTO valorretiradasaquemodalidadeagenteArgumentValidation();

	ViolacaoDTO valorretiradasaqueprestadordoservicodesaqueArgumentValidation();

	ViolacaoDTO valorretiradatrocovalorArgumentValidation();

	ViolacaoDTO valorretiradatrocomodalidadealteracaoArgumentValidation();

	ViolacaoDTO valorretiradatrocomodalidadeagenteArgumentValidation();

	ViolacaoDTO valorretiradatrocoprestadordoservicodesaqueArgumentValidation();

	ViolacaoDTO localbranchArgumentValidation();

	ViolacaoDTO accountnumberArgumentValidation();

	ViolacaoDTO taxidArgumentValidation();

	ViolacaoDTO aliasstatusArgumentValidation();

	ViolacaoDTO customerInformationValidation();

	ViolacaoDTO customerAuthorizationValidation();

}
