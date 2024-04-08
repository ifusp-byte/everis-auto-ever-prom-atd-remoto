package br.gov.caixa.siavl.atendimentoremoto.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto.AuditoriaPncInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.dto.AuditoriaPncProtocoloInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.auditoria.pnc.gateway.AuditoriaPncGateway;
import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloInputDTO;
import br.gov.caixa.siavl.atendimentoremoto.dto.GeraProtocoloOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.model.AtendimentoCliente;
import br.gov.caixa.siavl.atendimentoremoto.repository.GeraProtocoloRespository;
import br.gov.caixa.siavl.atendimentoremoto.service.GeraProtocoloService;
import br.gov.caixa.siavl.atendimentoremoto.sicli.dto.ContaAtendimentoOutputDTO;
import br.gov.caixa.siavl.atendimentoremoto.sicli.gateway.SicliGateway;
import br.gov.caixa.siavl.atendimentoremoto.util.TokenUtils;

@Service
@SuppressWarnings({ "squid:S6813" })
public class GeraProtocoloServiceImpl implements GeraProtocoloService {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	GeraProtocoloRespository geraProtocoloRespository;

	@Autowired
	AuditoriaPncGateway auditoriaPncGateway;
	
	@Autowired
	SicliGateway sicliGateway;
	
	private static final String DEFAULT_USER_IP = "123";

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public GeraProtocoloOutputDTO geraProtocolo(String token, GeraProtocoloInputDTO geraProtocoloInputDTO) throws Exception {
		
		Long matriculaAtendente = Long.parseLong(tokenUtils.getMatriculaFromToken(token).replaceAll("[a-zA-Z]", ""));
		Long cpfCnpj = Long.parseLong(geraProtocoloInputDTO.getCpfCnpj().trim()); 
		String canalAtendimento = geraProtocoloInputDTO.getTipoAtendimento();
	
		AtendimentoCliente atendimentoCliente = new AtendimentoCliente();
		
		atendimentoCliente.setMatriculaAtendente(matriculaAtendente);
		atendimentoCliente.setCanalAtendimento(canalAtendimento.charAt(0));

		if (geraProtocoloInputDTO.getCpfCnpj().trim().length() == 11) {
			atendimentoCliente.setCpfCliente(cpfCnpj);
		} else {
			ContaAtendimentoOutputDTO contaAtendimento = sicliGateway.contaAtendimento(token, geraProtocoloInputDTO.getCpfCnpj().trim());
			atendimentoCliente.setNomeCliente(contaAtendimento.getNomeCliente());
			atendimentoCliente.setCnpjCliente(cpfCnpj);
		}

		atendimentoCliente.setDataInicialAtendimento(formataDataBanco());
		atendimentoCliente = geraProtocoloRespository.save(atendimentoCliente);

		GeraProtocoloOutputDTO geraProtocoloOutputDTO = new GeraProtocoloOutputDTO();
		geraProtocoloOutputDTO.setStatus(true);
		geraProtocoloOutputDTO.setNumeroProtocolo(String.valueOf(atendimentoCliente.getNumeroProtocolo()));
		
		AuditoriaPncProtocoloInputDTO auditoriaPncProtocoloInputDTO = new AuditoriaPncProtocoloInputDTO(); 
		auditoriaPncProtocoloInputDTO = AuditoriaPncProtocoloInputDTO.builder()
				.cpfCnpj(String.valueOf(cpfCnpj))
				.canal(canalAtendimento)
				.numeroProtocolo(String.valueOf(atendimentoCliente.getNumeroProtocolo()))
				.dataInicioAtendimento(String.valueOf(new Date()))
				.matriculaAtendente(String.valueOf(matriculaAtendente))			
				.transacaoSistema("287")
				.build();
		
		String descricaoTransacao = null;

		try {
			descricaoTransacao = mapper.writeValueAsString(auditoriaPncProtocoloInputDTO);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

		AuditoriaPncInputDTO auditoriaPncInputDTO = new AuditoriaPncInputDTO();

		auditoriaPncInputDTO = AuditoriaPncInputDTO.builder()
				.descricaoTransacao(descricaoTransacao)
				.ipTerminalUsuario(DEFAULT_USER_IP)
				.nomeMfe("mfe_avl_atendimentoremoto")
				.numeroUnidadeLotacaoUsuario(50)
				.build();

		auditoriaPncGateway.auditoriaPncSalvar(token, auditoriaPncInputDTO);

		return geraProtocoloOutputDTO;
	}
	
	private Date formataDataBanco() {

		Calendar time = Calendar.getInstance();
		time.add(Calendar.HOUR, -3);
		return time.getTime();
	}

}
