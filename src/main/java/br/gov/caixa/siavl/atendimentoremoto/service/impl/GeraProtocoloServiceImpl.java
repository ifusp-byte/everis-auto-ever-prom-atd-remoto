package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.constants.GeraProtocoloServiceConstans.CONTAS;
import static br.gov.caixa.siavl.atendimentoremoto.constants.GeraProtocoloServiceConstans.DADOS_CNPJ;
import static br.gov.caixa.siavl.atendimentoremoto.constants.GeraProtocoloServiceConstans.DADOS_CPF;
import static br.gov.caixa.siavl.atendimentoremoto.constants.GeraProtocoloServiceConstans.INFORMAR_CPF_CNPJ_CLIENTE;
import static br.gov.caixa.siavl.atendimentoremoto.constants.GeraProtocoloServiceConstans.INVALIDOS;
import static br.gov.caixa.siavl.atendimentoremoto.constants.GeraProtocoloServiceConstans.MENSAGEM_PADRAO_ERRO_PROTOCOLO;
import static br.gov.caixa.siavl.atendimentoremoto.constants.GeraProtocoloServiceConstans.MENSAGEM_SICLI;
import static br.gov.caixa.siavl.atendimentoremoto.constants.GeraProtocoloServiceConstans.NOME_CLIENTE;
import static br.gov.caixa.siavl.atendimentoremoto.constants.GeraProtocoloServiceConstans.RAZAO_SOCIAL;
import static br.gov.caixa.siavl.atendimentoremoto.constants.GeraProtocoloServiceConstans.SOCIOS;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.AMBIENTE_NACIONAL;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DOCUMENT_TYPE_CNPJ;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.DOCUMENT_TYPE_CPF;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.NOME_MFE_AVL_ATENDIMENTOREMOTO;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.REGEX_REPLACE_LETRAS;
import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.TRANSACAO_SISTEMA_ENVIA_PROTOCOLO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.gov.caixa.siavl.atendimentoremoto.dto.ExceptionOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sicli.dto.ContaAtendimentoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sicli.gateway.SicliGateway;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto.AuditoriaPncInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.dto.AuditoriaPncProtocoloInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.gateway.sipnc.gateway.AuditoriaPncGateway;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.GeraProtocoloRespository;
import br.gov.caixa.siavl.atendimentoremoto.service.GeraProtocoloService;
import br.gov.caixa.siavl.atendimentoremoto.util.DataUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.DocumentoUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.MetodosUtils;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings("all")
public class GeraProtocoloServiceImpl implements GeraProtocoloService {

	@Autowired
	DataUtils dataUtils;

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	MetodosUtils metodosUtils;

	@Autowired
	SicliGateway sicliGateway;

	@Autowired
	DocumentoUtils documentoUtils;

	@Autowired
	AuditoriaPncGateway auditoriaPncGateway;

	@Autowired
	GeraProtocoloRespository geraProtocoloRespository;

	@Override
	public Object geraProtocolo(String token, GeraProtocoloInputDTO geraProtocoloInputDTO) throws Exception {

		ExceptionOutputDto erroProtocoloOutputDto = new ExceptionOutputDto();
		List<String> mensagensErroProtocolo = new ArrayList<>();

		String cpfCnpjFormat = documentoUtils.formataDocumento(geraProtocoloInputDTO.getCpfCnpj());

		if (!NumberUtils.isParsable(cpfCnpjFormat)) {
			erroProtocoloOutputDto.setMensagem(MENSAGEM_PADRAO_ERRO_PROTOCOLO);
			mensagensErroProtocolo.add(INFORMAR_CPF_CNPJ_CLIENTE);
			erroProtocoloOutputDto.setErros(Arrays.asList(mensagensErroProtocolo));
			return erroProtocoloOutputDto;
		}

		ContaAtendimentoOutputDTO contaAtendimento = sicliGateway.contaAtendimento(token, cpfCnpjFormat, false);

		String tipoDocumento = null;
		tipoDocumento = documentoUtils.retornaCpf(cpfCnpjFormat) ? DOCUMENT_TYPE_CPF : DOCUMENT_TYPE_CNPJ;

		if (DOCUMENT_TYPE_CPF.equals(tipoDocumento)) {
			if (contaAtendimento.getContas().isEmpty() 
					|| StringUtils.isBlank(contaAtendimento.getNomeCliente())) {
				erroProtocoloOutputDto.setMensagem(MENSAGEM_PADRAO_ERRO_PROTOCOLO);
				mensagensErroProtocolo.add(DADOS_CPF + cpfCnpjFormat + INVALIDOS);
				mensagensErroProtocolo.add(MENSAGEM_SICLI + contaAtendimento.getMensagemSicli());
				mensagensErroProtocolo.add(CONTAS + contaAtendimento.getContas());
				mensagensErroProtocolo.add(NOME_CLIENTE + contaAtendimento.getNomeCliente());
				erroProtocoloOutputDto.setErros(Arrays.asList(mensagensErroProtocolo));
				return erroProtocoloOutputDto;
			}
		}

		if (DOCUMENT_TYPE_CNPJ.equals(tipoDocumento)) {
			if (contaAtendimento.getContas().isEmpty() 
					|| contaAtendimento.getSocios().isEmpty()
					|| StringUtils.isBlank(contaAtendimento.getNomeCliente())
					|| StringUtils.isBlank(contaAtendimento.getRazaoSocial())) {
				erroProtocoloOutputDto.setMensagem(MENSAGEM_PADRAO_ERRO_PROTOCOLO);
				mensagensErroProtocolo.add(DADOS_CNPJ + cpfCnpjFormat + INVALIDOS);
				mensagensErroProtocolo.add(MENSAGEM_SICLI + contaAtendimento.getMensagemSicli());
				mensagensErroProtocolo.add(CONTAS + contaAtendimento.getContas());
				mensagensErroProtocolo.add(SOCIOS + contaAtendimento.getSocios());
				mensagensErroProtocolo.add(NOME_CLIENTE + contaAtendimento.getNomeCliente());
				mensagensErroProtocolo.add(RAZAO_SOCIAL + contaAtendimento.getRazaoSocial());
				erroProtocoloOutputDto.setErros(Arrays.asList(mensagensErroProtocolo));
				return erroProtocoloOutputDto;
			}
		}

		Long cpfCnpj = Long.parseLong(cpfCnpjFormat);
		Long numeroUnidade = Long.parseLong(tokenUtils.getUnidadeFromToken(token));
		String canalAtendimento = geraProtocoloInputDTO.getTipoAtendimento();
		Long matriculaAtendente = Long.parseLong(tokenUtils.getMatriculaFromToken(token).replaceAll(REGEX_REPLACE_LETRAS, StringUtils.EMPTY));

		AtendimentoCliente atendimentoCliente = new AtendimentoCliente();
		atendimentoCliente.setMatriculaAtendente(matriculaAtendente);
		atendimentoCliente.setCanalAtendimento(canalAtendimento.charAt(0));
		atendimentoCliente.setNumeroUnidade(numeroUnidade);
		atendimentoCliente.setNomeCliente(documentoUtils.retornaCpf(cpfCnpjFormat) ? contaAtendimento.getNomeCliente() : contaAtendimento.getRazaoSocial());
		atendimentoCliente.setCpfCliente(cpfCnpj);
		atendimentoCliente.setDataInicialAtendimento(dataUtils.formataDataBanco());
		atendimentoCliente.setDataContatoCliente(dataUtils.formataDataBanco());
		atendimentoCliente = geraProtocoloRespository.save(atendimentoCliente);

		GeraProtocoloOutputDTO geraProtocoloOutputDTO = new GeraProtocoloOutputDTO();
		geraProtocoloOutputDTO.setNumeroProtocolo(String.valueOf(atendimentoCliente.getNumeroProtocolo()));
		geraProtocoloOutputDTO.setSocios(contaAtendimento.getSocios());
		geraProtocoloOutputDTO.setRazaoSocial(contaAtendimento.getRazaoSocial());
		geraProtocoloOutputDTO.setStatusSicli(contaAtendimento.getStatusCode());
		geraProtocoloOutputDTO.setMensagemSicli(contaAtendimento.getStatusMessage());
		geraProtocoloOutputDTO.setStatus(true);

		AuditoriaPncProtocoloInputDTO auditoriaPncProtocoloInputDTO = new AuditoriaPncProtocoloInputDTO();
		auditoriaPncProtocoloInputDTO = AuditoriaPncProtocoloInputDTO.builder()
				.cpfCnpj(String.valueOf(cpfCnpj))
				.canal(canalAtendimento)
				.numeroProtocolo(String.valueOf(atendimentoCliente.getNumeroProtocolo()))
				.dataInicioAtendimento(dataUtils.formataData(new Date()))
				.matriculaAtendente(String.valueOf(matriculaAtendente))
				.transacaoSistema(TRANSACAO_SISTEMA_ENVIA_PROTOCOLO)
				.build();

		String descricaoTransacao = metodosUtils.writeValueAsString(auditoriaPncProtocoloInputDTO);

		AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();
		auditoriaPncInputDTO = AuditoriaPncInputDTO.builder()
				.descricaoTransacao(descricaoTransacao)
				.ipTerminalUsuario(tokenUtils.getIpFromToken(token))
				.nomeMfe(NOME_MFE_AVL_ATENDIMENTOREMOTO)
				.numeroUnidadeLotacaoUsuario(50L)
				.ambienteAplicacao(AMBIENTE_NACIONAL)
				.tipoDocumento(tipoDocumento)
				.numeroIdentificacaoCliente(cpfCnpj)
				.build();

		auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);

		return geraProtocoloOutputDTO;
	}

}
