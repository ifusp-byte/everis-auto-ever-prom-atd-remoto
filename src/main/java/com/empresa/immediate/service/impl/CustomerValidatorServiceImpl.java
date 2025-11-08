package com.empresa.immediate.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.empresa.immediate.adapter.CustomerAdapter;
import com.empresa.immediate.dto.ViolacaoDTO;
import com.empresa.immediate.service.CustomerValidatorService;
import com.empresa.immediate.service.ImmediateCreateValidatorService;
import com.empresa.immediate.util.constant.ImmediateCreateConstant;
import com.empresa.immediate.util.method.ImmediateMethod;

@Service
public class CustomerValidatorServiceImpl implements CustomerValidatorService {

	@Autowired
	ImmediateMethod util;

	@Autowired
	CustomerAdapter customerAdapter;

	@Autowired
	ImmediateCreateValidatorService immediateCreateValidatorService;

	private ResponseEntity<Object> immediateCreateResponse;

	private String returnTaxId;
	private String returnAccount;
	private String returnClientId;
	private String returnConnectivityByApi;
	private String returnStatus;

	private String headerTaxId;
	private String headerAccount;
	private String headerClientId;
	private String headerApimGuid;

	static Logger logger = Logger.getLogger(CustomerValidatorServiceImpl.class.getName());

	public Boolean customerInformation(Map<String, String> immediateCreateHeaders) {
		
		headerTaxId = immediateCreateHeaders.get(ImmediateCreateConstant.HEADER_TAXID);
		headerAccount = immediateCreateHeaders.get(ImmediateCreateConstant.HEADER_ACCOUNT_NUMBER);
		headerClientId = immediateCreateHeaders.get(ImmediateCreateConstant.HEADER_APIM_CLIENT_ID);
		headerApimGuid = immediateCreateHeaders.get(ImmediateCreateConstant.HEADER_APIM_GUID);
		
		try {
			
			var requestCustomerInformation = new JSONObject();
			
			requestCustomerInformation.put("apimClientId", headerClientId);
			requestCustomerInformation.put("account", headerAccount);
			requestCustomerInformation.put("taxid", headerTaxId);
			
			String customerInformation = null;
			
			customerInformation = customerAdapter.settingsApiDynamic(requestCustomerInformation.toString());
			
			var responseCustomerInformation = new JSONObject();
			
			returnTaxId = responseCustomerInformation.get("taxid").toString();
			returnAccount = responseCustomerInformation.get("account").toString();
			returnClientId = responseCustomerInformation.get("clientId").toString();
			returnConnectivityByApi = responseCustomerInformation.get("connectivityByApi").toString();
			returnStatus = responseCustomerInformation.get("status").toString();
			
		} catch (JSONException | SecurityException e) {
			
			logger.log(Level.SEVERE, e.getLocalizedMessage());
			
		}
		
		return customerValidator(returnClientId, returnTaxId, returnAccount, returnConnectivityByApi, returnStatus, headerApimGuid);
		
	}

	@Override
	public ResponseEntity<Object> customerResponse() {

		return immediateCreateResponse;
	}

	public ResponseEntity<Object> customerInformationResponse(String headerApimGuid, List<ViolacaoDTO> violacoes) {

		immediateCreateResponse = util.responseEntityBuild(HttpStatus.BAD_REQUEST, null,

				util.errorResultBuild(headerApimGuid, ImmediateCreateConstant.MSG_STATUS_400,
						ImmediateCreateConstant.MSG_TYPE_400_2, ImmediateCreateConstant.MSG_TITLE_400,
						ImmediateCreateConstant.MSG_DETAIL_400_4, violacoes));

		return immediateCreateResponse;

	}

	public ResponseEntity<Object> customerAuthorizationResponse(String headerApimGuid, List<ViolacaoDTO> violacoes) {

		immediateCreateResponse = util.responseEntityBuild(HttpStatus.UNAUTHORIZED, null,

				util.errorResultBuild(headerApimGuid, ImmediateCreateConstant.MSG_STATUS_401,
						ImmediateCreateConstant.MSG_TYPE_401, ImmediateCreateConstant.MSG_TITLE_401,
						ImmediateCreateConstant.MSG_DETAIL_401, violacoes));

		return immediateCreateResponse;

	}

	public Boolean customerValidator(String apimClientId, String taxid, String account, String conectivityByApi,
			String status, String headerApimGuid) {

		Boolean statusReturn = false;
		Boolean statusReturnInformation = false;
		Boolean statusReturnAuthorization = false;

		List<ViolacaoDTO> violacoes = new ArrayList<>();

		if (Boolean.TRUE.equals(customerInformationValidator(apimClientId, taxid, account))) {

			statusReturnInformation = true;
		}

		if (Boolean.TRUE.equals(customerAuthorizationValidator(conectivityByApi, status))) {

			statusReturnAuthorization = true;

		}

		if ((Boolean.TRUE.equals(statusReturnInformation)) && ((Boolean.TRUE.equals(statusReturnAuthorization))
				|| (Boolean.TRUE.equals(statusReturnAuthorization)))) {

			statusReturn = statusReturnInformation;

			violacoes.add(immediateCreateValidatorService.customerInformationValidation());
			customerInformationResponse(headerApimGuid, violacoes);

		} else if ((Boolean.FALSE.equals(statusReturnInformation))
				&& (Boolean.TRUE.equals(statusReturnAuthorization))) {

			statusReturn = statusReturnAuthorization;

			violacoes.add(immediateCreateValidatorService.customerInformationValidation());
			customerInformationResponse(headerApimGuid, violacoes);

		}
		
		return statusReturn;
	}

	public Boolean customerInformationValidator(String apimClientId, String taxid, String account) {

		Boolean statusReturn = false;

		if (Boolean.TRUE.equals(apimClientIdValidator(apimClientId)) || Boolean.TRUE.equals(taxIdValidator(taxid))
				|| Boolean.TRUE.equals(accountNumber(account))) {

			statusReturn = true;

		}

		return statusReturn;
	}

	public Boolean customerAuthorizationValidator(String conectivityByApi, String status) {

		Boolean statusReturn = false;

		if (apiOptionValidator(conectivityByApi) || statusValidator(status)) {

			statusReturn = true;
		}

		return statusReturn;
	}

	public Boolean apimClientIdValidator(String apimClientId) {

		Boolean statusReturn = false;

		if (!apimClientId.equalsIgnoreCase(headerClientId)) {

			statusReturn = true;
		}

		return statusReturn;

	}

	public Boolean taxIdValidator(String taxid) {

		Boolean statusReturn = false;

		if (!taxid.equalsIgnoreCase(headerClientId)) {

			statusReturn = true;
		}

		return statusReturn;

	}

	public Boolean accountNumber(String account) {

		Boolean statusReturn = false;

		if (!account.equalsIgnoreCase(headerAccount)) {

			statusReturn = true;
		}

		return statusReturn;

	}

	public Boolean apiOptionValidator(String conectivityByApi) {

		Boolean statusReturn = false;

		if (!Boolean.TRUE.equals(Boolean.parseBoolean(conectivityByApi))) {

			statusReturn = true;
		}

		return statusReturn;

	}

	public Boolean statusValidator(String status) {

		Boolean statusReturn = false;

		if (!Boolean.TRUE.equals(Boolean.parseBoolean(status))) {

			statusReturn = true;
		}

		return statusReturn;

	}

}
