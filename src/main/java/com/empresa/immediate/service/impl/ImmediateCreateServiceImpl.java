package com.empresa.immediate.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.empresa.immediate.adapter.CustomerAdapter;
import com.empresa.immediate.dao.ImmediateCreateDAO;
import com.empresa.immediate.dto.CalendarioDTO;
import com.empresa.immediate.dto.DevedorDTO;
import com.empresa.immediate.dto.ImmediateCreateRequestDTO;
import com.empresa.immediate.dto.ImmediateCreateResponseDTO;
import com.empresa.immediate.dto.ValorDTO;
import com.empresa.immediate.service.CustomerValidatorService;
import com.empresa.immediate.service.ImmediateCreateService;
import com.empresa.immediate.util.method.ImmediateMethod;

@Service
public class ImmediateCreateServiceImpl implements ImmediateCreateService {

	@Autowired
	ImmediateMethod util;

	@Autowired
	ImmediateCreateDAO immediateCreateDAO;

	@Autowired
	CustomerAdapter customerAdapter;

	@Autowired
	CustomerValidatorService customerValidatorService;

	static Logger logger = Logger.getLogger(ImmediateCreateServiceImpl.class.getName());

	@SuppressWarnings("unchecked")
	public ResponseEntity<Object> immediateCreate(Map<String, String> immediateCreateHeaders,
			ImmediateCreateRequestDTO immediateCreateRequestDTO) {
		
		
		
		Map<String, String> immediateCreateHeadersResponse = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		immediateCreateHeadersResponse.putAll(immediateCreateHeaders);
		
		ResponseEntity<Object> immediateCreateResponse = null;
		
		if(Boolean.TRUE.equals(customerValidatorService.customerInformation(immediateCreateHeadersResponse)) ) {
			
			try {
				
				immediateCreateResponse = (ResponseEntity<Object>) CustomerValidatorService.class.getDeclaredMethod("customerResponse").invoke(customerValidatorService);
				
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				
				logger.log(Level.SEVERE, e.getLocalizedMessage());
			}
			
		} else {
			
			var accessToken = UUID.randomUUID();
			
			var immediateCreateResponseDTO = ImmediateCreateResponseDTO.builder()
					.calendario(null)
					.txid(null)
					.revisao(0)
					.status(null)
					.devedor(null)
					.valor(null)
					.chave(null)
					.solicitacaoPagador(null)
					.pixCopiaECola(null)
					.infoAdicionais(null)
					.build();
			
			immediateCreateDAO.immediateCreateMvto(immediateCreateHeadersResponse, immediateCreateResponseDTO, accessToken);
			
			immediateCreateResponse = ResponseEntity
					.status(HttpStatus.CREATED)
					.body(immediateCreateResponseDTO);
		}
		
		
		
		
		
		return immediateCreateResponse;
	}

	public CalendarioDTO calendarioBuild(CalendarioDTO calendarioDTO) {

		return CalendarioDTO.builder().expiracao(calendarioDTO.getExpiracao()).criacao(new Date()).build();

	}

	public DevedorDTO devedorBuild(DevedorDTO devedorDTO) {

		var devedorResponseDTO = new DevedorDTO();

		if ((devedorDTO.getCnpj() == null) || (devedorDTO.getCnpj().isBlank())) {

			devedorResponseDTO = DevedorDTO.builder().cpf(devedorDTO.getCpf()).nome(devedorDTO.getNome()).build();

		} else {

			devedorResponseDTO = DevedorDTO.builder().cpf(devedorDTO.getCpf()).nome(devedorDTO.getNome()).build();

		}

		return devedorResponseDTO;
	}

	public ValorDTO valorBuild(ValorDTO valorDTO) {

		return ValorDTO.builder().original(valorDTO.getOriginal())
				.modalidadeAlteracao(valorDTO.getModalidadeAlteracao()).build();
	}

	public String pixCopiaEColaBuild(Map<String, String> immediateCreateHeadersResponse,
			ImmediateCreateRequestDTO immediateCreateRequestDTO, UUID accessToken) {

		return "" + "01" + "BR.GOV.BCB.PIX" + "" + "0000" + "900" + "BR" + "MARI" + "BRASILIA" + "***"
				+ immediateCreateRequestDTO.getValor().getOriginal() + "12" + "" + "(ROUTE)" + accessToken;

	}

}
