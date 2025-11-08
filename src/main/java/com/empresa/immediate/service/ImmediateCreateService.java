package com.empresa.immediate.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.empresa.immediate.dto.ImmediateCreateRequestDTO;

public interface ImmediateCreateService {
	
	ResponseEntity<Object> immediateCreate(Map<String, String> immediateCreateHeaders, ImmediateCreateRequestDTO immediateCreateRequestDTO);

}
