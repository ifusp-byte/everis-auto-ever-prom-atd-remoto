package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.CriaNotaInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.enums.TipoAtendimentoEnum;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.GeraProtocoloRespository;
import br.gov.caixa.siavl.atendimentoremoto.service.GeraProtocoloService;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings("all")
public class GeraProtocoloServiceImpl implements GeraProtocoloService {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	GeraProtocoloRespository geraProtocoloRespository;

	private static final String DOCUMENT_TYPE_CPF = "CPF";
	private static final String DOCUMENT_TYPE_CNPJ = "CNPJ";
	private static final String TIPO_ATENDIMENTO = "tipo_de_atendimento";

	@Override
	public CriaNotaInputDTO geraProtocolo(CriaNotaInputDTO criaNotaInputDto) {

		String tipoDocumento = null;
		Long matriculaAtendente = Long
				.parseLong(tokenUtils.getMatriculaFromToken(criaNotaInputDto.getToken()).replaceAll("[a-zA-Z]", ""));
		Long cpfCnpj = Long
				.parseLong(String.valueOf(criaNotaInputDto.getCpfCnpj()).replace(".", "").replace("-", "").replace("/", "").trim());
		Long numeroUnidade = Long.parseLong(tokenUtils.getUnidadeFromToken(criaNotaInputDto.getToken()));

		String canalAtendimento = "A"; // definido no IB 22694358 - Sprint 21
		AtendimentoCliente atendimentoCliente = new AtendimentoCliente();

		atendimentoCliente.setMatriculaAtendente(matriculaAtendente);
		atendimentoCliente.setCanalAtendimento(canalAtendimento.charAt(0));	
		atendimentoCliente.setCanalAtendimento(canalAtendimento.charAt(0));
		atendimentoCliente.setNumeroCanalAtendimento(11L);
		atendimentoCliente.setNumeroUnidade(numeroUnidade); // gravamos unidade na tb01, de acordo com o IB 22733080 - Sprint 22 
		atendimentoCliente.setSituacaoIdPositiva(0L);
		atendimentoCliente.setDescricaoIdentificacaoPositiva("NÃO REALIZADA");
		atendimentoCliente.setDataIdentificacaoPositiva(formataDataBanco());
		atendimentoCliente.setDataValidacaoPositiva(formataDataBanco());
		
		if (String.valueOf(criaNotaInputDto.getCpfCnpj()).trim().length() == 11) {
			atendimentoCliente.setNomeCliente(String.valueOf(criaNotaInputDto.getNomeCliente()));
			atendimentoCliente.setCpfCliente(cpfCnpj);
			tipoDocumento = DOCUMENT_TYPE_CPF;

		} else {

			atendimentoCliente.setNomeCliente(String.valueOf(criaNotaInputDto.getNomeCliente()));
			atendimentoCliente.setCnpjCliente(cpfCnpj);
			atendimentoCliente.setCpfCliente(Long.parseLong(String.valueOf(criaNotaInputDto.getCpfSocio())));
			tipoDocumento = DOCUMENT_TYPE_CNPJ;

		}

		atendimentoCliente.setDataInicialAtendimento(formataDataBanco());
		atendimentoCliente.setDataContatoCliente(formataDataBanco());
		atendimentoCliente = geraProtocoloRespository.save(atendimentoCliente);

		criaNotaInputDto.setNumeroProtocolo(String.valueOf(atendimentoCliente.getNumeroProtocolo()));

		return criaNotaInputDto;

	}

	private Date formataDataBanco() {

		Calendar time = Calendar.getInstance();
		time.add(Calendar.HOUR, -3);
		return time.getTime();
	}

	private String formataData(Date dateInput) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);
		data = String.valueOf(sdfOut.format(dateInput));
		return data;
	}

}
