package com.empresa.immediate.util.method;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.empresa.immediate.dto.ErrorResultDTO;
import com.empresa.immediate.dto.ViolacaoDTO;

@Component
public class ImmediateMethod {
	
	public ErrorResultDTO errorResultBuild(String apim_guid, int status, String type, String title, String detail, List<ViolacaoDTO> violacoes) {
		
		return ErrorResultDTO.builder()
				.violacoes(violacoes)
				.status(status)
				.type(type)
				.title(title)
				.detail(detail)
				.correlationId(apim_guid.replace("\"", ""))
				.build();
	}
	
	
	public ResponseEntity<Object> responseEntityBuild(HttpStatus status, HttpHeaders headers, ErrorResultDTO body) {
		
		return ResponseEntity
				.status(status)
				.headers(headers)
				.body(body);
	}
	
	

}
