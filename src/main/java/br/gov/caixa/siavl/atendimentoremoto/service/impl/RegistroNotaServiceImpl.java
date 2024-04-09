package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.sql.Clob;
import java.util.Calendar;
import java.util.Date;

import javax.sql.rowset.serial.SerialClob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.service.AuditoriaRegistraNotaService;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoNegocio;
import br.gov.caixa.siavl.atendimentoremoto.model.RelatorioNotaNegociacao;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.EquipeAtendimentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.RelatorioNotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.RegistroNotaService;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;
import lombok.Data;

@Service
public class RegistroNotaServiceImpl implements RegistroNotaService {
	
	@Autowired
	AuditoriaRegistraNotaService auditoriaRegistraNotaService; 

	@Autowired
	RelatorioNotaNegociacaoRepository relatorioNotaNegociacaoRepository;

	@Autowired
	EquipeAtendimentoRepository equipeAtendimentoRepository;

	@Autowired
	NotaNegociacaoRepository notaNegociacaoRepository;
	
	@Autowired
	AtendimentoClienteRepository atendimentoClienteRepository;

	@Autowired
	TokenUtils tokenUtils;
	
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public Object registraNota(String token, Long numeroNota, RegistraNotaInputDto registraNotaInputDto)
			throws Exception {

		Long numeroUnidade = Long.parseLong(tokenUtils.getUnidadeFromToken(token));
		Long numeroEquipe = null;
		String cpfCnpj = null; 
		String matriculaAtendente = tokenUtils.getMatriculaFromToken(token).replaceAll("[a-zA-Z]", "");
		boolean statusRetornoSicli = true;
		String numeroProtocolo = registraNotaInputDto.getNumeroProtocolo();
		String numeroContaAtendimento = registraNotaInputDto.getContaAtendimento();
		String versaoSistema = registraNotaInputDto.getVersaoSistema();
		
		numeroEquipe = equipeAtendimentoRepository.findEquipeByUnidade(numeroUnidade);

		RegistraNotaOutputDto registraNotaOutputDto = new RegistraNotaOutputDto();

		if (numeroEquipe == null) {

			registraNotaOutputDto = RegistraNotaOutputDto.builder().statusNotaRegistrada(false)
					.mensagem("A unidade não possui equipe vinculada.").build();

		} else {		
			
			AtendimentoCliente atendimentoCliente = new AtendimentoCliente(); 
			atendimentoCliente.setNumeroProtocolo(Long.parseLong(registraNotaInputDto.getNumeroProtocolo())); 
			atendimentoCliente.setNomeCliente(registraNotaInputDto.getNomeCliente());
				
			if (registraNotaInputDto.getCpfCnpj().length() == 11) {
				cpfCnpj = registraNotaInputDto.getCpfCnpj();
				atendimentoCliente.setCpfCliente(Long.parseLong(registraNotaInputDto.getCpfCnpj()));
			} else {
				cpfCnpj = registraNotaInputDto.getCpfCnpj();
				atendimentoCliente.setCnpjCliente(Long.parseLong(registraNotaInputDto.getCpfCnpj()));
			}
			
			String dsRelatorioNota = mapper.writeValueAsString(registraNotaInputDto);
			Clob relatorioNota = new SerialClob(dsRelatorioNota.toCharArray());
			
			RelatorioNotaNegociacao relatorioNotaNegociacao = new RelatorioNotaNegociacao(); 
			relatorioNotaNegociacao.setNumeroEquipe(numeroEquipe);		
			relatorioNotaNegociacao.setRelatorioNota(relatorioNota);	
			
			relatorioNotaNegociacaoRepository.save(relatorioNotaNegociacao);		
			registraNotaOutputDto = RegistraNotaOutputDto.builder()
					.statusNotaRegistrada(true)
					.mensagem("Nota registrada com sucesso!")
					.build();
		}
		
		auditoriaRegistraNotaService.auditar(String.valueOf(formataDataBanco()), token, cpfCnpj, matriculaAtendente, String.valueOf(statusRetornoSicli), numeroProtocolo, numeroContaAtendimento, String.valueOf(numeroNota), versaoSistema);
		return registraNotaOutputDto;
	}

	@Override
	public Boolean enviaCliente(Long numeroNota) {
		Boolean statusContratacao = null;
		
		notaNegociacaoRepository.enviaNotaCliente(numeroNota);
		statusContratacao = true;
		
		return statusContratacao;
	}
	
	private Date formataDataBanco() {

		Calendar time = Calendar.getInstance();
		time.add(Calendar.HOUR, -3);
		return time.getTime();
	}

}
