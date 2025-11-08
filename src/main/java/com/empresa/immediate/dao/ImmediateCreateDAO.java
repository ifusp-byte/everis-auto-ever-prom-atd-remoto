package com.empresa.immediate.dao;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.empresa.immediate.dto.ImmediateCreateResponseDTO;

@Service
public interface ImmediateCreateDAO {
	
	void immediateCreateMvto(Map<String, String> immediateCreateHeaders, ImmediateCreateResponseDTO immediateCreateResponseDTO, UUID accessToken);

}
