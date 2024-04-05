package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.sql.Clob;

import javax.sql.rowset.serial.SerialClob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaInputDto;
import br.gov.caixa.siavl.atendimentoremoto.dto.RegistraNotaOutputDto;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.model.RelatorioNotaNegociacao;
import br.gov.caixa.siavl.atendimentoremoto.repository.AtendimentoClienteRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.EquipeAtendimentoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.NotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.repository.RelatorioNotaNegociacaoRepository;
import br.gov.caixa.siavl.atendimentoremoto.service.RegistroNotaService;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
public class RegistroNotaServiceImpl implements RegistroNotaService {

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
				atendimentoCliente.setCpfCliente(Long.parseLong(registraNotaInputDto.getCpfCnpj()));
			} else {
				atendimentoCliente.setCnpjCliente(Long.parseLong(registraNotaInputDto.getCpfCnpj()));
			}
			
			atendimentoClienteRepository.save(atendimentoCliente);
			
			String dsRelatorioNota = mapper.writeValueAsString(registraNotaInputDto);
			Clob relatorioNota = new SerialClob(dsRelatorioNota.toCharArray());
			
			RelatorioNotaNegociacao relatorioNotaNegociacao = new RelatorioNotaNegociacao(); 
			relatorioNotaNegociacao.setNumeroEquipe(numeroEquipe);		
			relatorioNotaNegociacao.setRelatorioNota(relatorioNota);	
			
			relatorioNotaNegociacaoRepository.save(relatorioNotaNegociacao);		
			registraNotaOutputDto = RegistraNotaOutputDto.builder().statusNotaRegistrada(true)
					.mensagem("Nota registrada com sucesso!").build();
		}

		return registraNotaOutputDto;
	}

	@Override
	public Boolean enviaCliente(Long numeroNota) {
		Boolean statusContratacao = null;
		
		notaNegociacaoRepository.enviaNotaCliente(numeroNota);
		statusContratacao = true;
		
		return statusContratacao;
	}

}
