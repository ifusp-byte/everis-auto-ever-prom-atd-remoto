package com.empresa.immediate.controller;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.immediate.dto.ImmediateCreateRequestDTO;
import com.empresa.immediate.service.ImmediateCreateService;

@Validated
@RestController
@CrossOrigin("empresaPixAllowed.com")
@RequestMapping(value = "/v1/empresa/immediate")
public class ImmediateCreateController {

	@Autowired
	private ImmediateCreateService immediateCreateService;

	@PutMapping("/createimmediatecollection")
	public ResponseEntity<Object> ImmediateCreate(

			@Valid @RequestHeader Map<String, String> immediateCreateHeaders,

			@Valid @RequestHeader(value = "message_time_stamp", required = false) String message_time_stamp,
			@Valid @Size(max = 5) @RequestHeader(value = "version", required = false) String version,
			@Valid @Size(max = 10) @RequestHeader(value = "source", required = false) String source,
			@Valid @Size(max = 25) @RequestHeader(value = "user_id", required = false) String user_id,

			@Valid @NotNull @NotBlank @Size(max = 36) @RequestHeader(value = "apim_client_id") String apim_client_id,
			@Valid /* @NotNull @Size(max = 36) /*@Pattern(regexp - "^[a-zA-Z\\d-_]+$) */ @RequestHeader(value = "apim_guid") String apim_guid,
			@Valid @NotNull @Positive @Max(9999) @RequestHeader(value = "localBranch") long localBranch,
			@Valid @NotNull @Positive @RequestHeader(value = "accountNumber") long accountNumber,
			@Valid @NotNull @Positive @RequestHeader(value = "taxid") long taxid,
			@Valid @NotNull @NotBlank @Pattern(regexp = "^(?i)[ACTIVE]+$") @RequestHeader(value = "aliasStatus") String aliasStatus,

			@Valid @RequestBody ImmediateCreateRequestDTO immediateCreateRequestDTO) {

		ResponseEntity<Object> immediateCreateResponse = null;
		immediateCreateResponse = immediateCreateService.immediateCreate(immediateCreateHeaders,
				immediateCreateRequestDTO);

		return immediateCreateResponse;
	}

}
