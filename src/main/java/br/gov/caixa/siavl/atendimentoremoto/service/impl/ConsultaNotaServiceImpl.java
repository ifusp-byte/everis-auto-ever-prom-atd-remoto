package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.text.MaskFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.caixa.siavl.atendimentoremoto.dto.AtendimentoClienteOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.ConsultaNotaService;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
public class ConsultaNotaServiceImpl implements ConsultaNotaService {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	AtendimentoClienteRepository atendimentoClienteRepository;

	public List<AtendimentoClienteOutputDto> consultaNota(String token) {

		Long numeroMatriculaCriacaoNota = null;
		numeroMatriculaCriacaoNota = Long.parseLong(tokenUtils.getMatriculaFromToken(token).replaceAll("[a-zA-Z]", ""));
		List<AtendimentoClienteOutputDto> atendimentos = new ArrayList<>();

		atendimentoClienteRepository.findByMatriculaAtendente(numeroMatriculaCriacaoNota).stream()
				.forEach(atendimentoCliente ->

				{
					AtendimentoClienteOutputDto atendimentoClienteOutputDto = null;

					atendimentoClienteOutputDto = AtendimentoClienteOutputDto.builder()
							.cpfCliente(String.valueOf(formataCpf(atendimentoCliente[0])))
							.cnpjCliente(String.valueOf(formataCnpj(atendimentoCliente[1])))
							.nomeCliente(String.valueOf(atendimentoCliente[2]))
							.produtoDescricao(String.valueOf(atendimentoCliente[3]))
							.dataCriacaoNota(String.valueOf(formataData(atendimentoCliente[4])))
							.idNegociacao(String.valueOf(atendimentoCliente[5]))
							.descricaoNota(String.valueOf(atendimentoCliente[6])).build();

					atendimentos.add(atendimentoClienteOutputDto);
				});
		return atendimentos;
	}

	private String formataData(Object object) {

		String data = null;
		Locale locale = new Locale("pt", "BR");
		SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'.0'", locale);
		SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale);

		try {
			data = String.valueOf(sdfOut.format(sdfIn.parse(String.valueOf(object))));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return data;
	}

	private String formataCpf(Object object) {

		String cpfInput = null;
		String formatCpf = null;
		String cpf = null;
		MaskFormatter cpfMask = null;

		if (object != null) {
			cpfInput = String.valueOf(object).replace(".", "").replace("/", "").replace("/", "").replace("-", "");
			formatCpf = "00000000000".substring(cpfInput.length()) + cpfInput;
			try {
				cpfMask = new MaskFormatter("###.###.###-##");
				cpfMask.setValueContainsLiteralCharacters(false);
				cpf = cpfMask.valueToString(formatCpf);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return cpf;
	}

	private String formataCnpj(Object object) {

		String cnpjInput = null;
		String formatCnpj = null;
		String cnpj = null;
		MaskFormatter cnpjMask = null;

		if (object != null) {
			cnpjInput = String.valueOf(object).replace(".", "").replace("/", "").replace("/", "").replace("-", "");
			formatCnpj = "00000000000000".substring(cnpjInput.length()) + cnpjInput;
			try {
				cnpjMask = new MaskFormatter("##.###.###/####-##");
				cnpjMask.setValueContainsLiteralCharacters(false);
				cnpj = cnpjMask.valueToString(formatCnpj);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return cnpj;
	}
}
