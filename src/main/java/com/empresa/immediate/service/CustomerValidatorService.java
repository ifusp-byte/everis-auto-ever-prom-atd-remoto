package com.empresa.immediate.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface CustomerValidatorService {
	
	Boolean customerInformation(Map<String, String> immediateCreateHeaders);
	
	ResponseEntity<Object> customerResponse();

}
