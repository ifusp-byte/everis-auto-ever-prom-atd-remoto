package com.empresa.immediate.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.empresa.immediate.adapter.CustomerAdapter;
import com.empresa.immediate.dto.ImmediateCreateRequestDTO;
import com.empresa.immediate.service.CustomerValidatorService;
import com.empresa.immediate.util.constant.ImmediateCreateConstant;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImmediateCreateControllerTest {

	@LocalServerPort
	private int port;

	private String defaultUrl = "http://localhost";

	private String immediateCreate = null;

	private static RestTemplate restTemplate;

	private static ObjectMapper mapper;

	@Mock
	CustomerValidatorService customerValidatorService;

	@Mock
	CustomerAdapter customerAdapter;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {

		immediateCreate = defaultUrl.concat(":").concat(port + "").concat("v1/empresa-faculdade-pix/immediate/create");

		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setVisibility(
				VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

	}

	@Test
	void ImmediatCreateSuccessTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateAllSuccessTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		ResponseEntity<String> immediateCreateResponseDTO = restTemplate.exchange(immediateCreate, HttpMethod.PUT,
				immediateCreateHttpEntity, String.class);

		Assertions.assertEquals(HttpStatus.CREATED, immediateCreateResponseDTO.getStatusCode());

	}

	@Test
	void ImmediateCreateDoubleBodyErrorTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateDoubleBodyErrorTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains("503"));

		}
	}

	@Test
	void ImmediateCreateDoubleHeaderErrorTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65888888888888888888888888888");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateAllSuccessTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains("503"));

		}
	}

	@Test
	void ImmediateCreateTxid1Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/1immediateCreateTxidTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_TXID;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateTxid2Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/2immediateCreateTxidTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_TXID;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateTxid3Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/3immediateCreateTxidTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_TXID;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateTxid4Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/4immediateCreateTxidTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_TXID;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateCalendarioExpiracaoTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/1immediateCreateCalendarioExpiracaoTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_CALENDARIO_EXPIRACAO;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateCalendarioExpiracao2Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/2immediateCreateCalendarioExpiracaoTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_CALENDARIO_EXPIRACAO;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateDevedorCpfTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateDevedorCpfTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_DEVEDOR;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateDevedorCnpjTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateDevedorCnpjTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_DEVEDOR;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateDevedorNomeTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateDevedorNomeTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_DEVEDOR;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateChave1Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/1immediateCreateChaveTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_CHAVE;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateChave2Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/2immediateCreateChaveTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_CHAVE;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateChave3Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/3immediateCreateChaveTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_CHAVE;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateValorOriginal1Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/1immediateCreateValorOriginalTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.BODY_VALOR_ORIGINAL;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateValorOriginal2Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/2immediateCreateValorOriginalTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.BODY_VALOR_ORIGINAL;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateValorOriginal3Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/3immediateCreateValorOriginalTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.BODY_VALOR_ORIGINAL;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateValorOriginal4Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/4immediateCreateValorOriginalTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.BODY_VALOR_ORIGINAL;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateValorOriginal5Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/5immediateCreateValorOriginalTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.BODY_VALOR_ORIGINAL;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateValorOriginal6Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/6immediateCreateValorOriginalTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.BODY_VALOR_ORIGINAL;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateValorOriginal7Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/7immediateCreateValorOriginalTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.BODY_VALOR_ORIGINAL;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateValorOriginal8Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/8immediateCreateValorOriginalTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.BODY_VALOR_ORIGINAL;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateValorOriginal9Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/9immediateCreateValorOriginalTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.BODY_VALOR_ORIGINAL;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateValorOriginal10Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/10immediateCreateValorOriginalTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.BODY_VALOR_ORIGINAL;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateSolicitacaoPagador3Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/3immediateCreateSolicitacaoPagadorTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_SOLICITACAOPAGADOR;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateValorModalidadeAlteracaoTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/3immediateCreateValorModalidadeAlteracaoTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_MODALIDADEALTERACAO;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateLocIdTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateLocIdTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_LOC_ID;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateInfoAdicionaisTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateInfoAdicionaisTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_INFOADICIONAIS;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateApimClientId1Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65888888888888888");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/1immediateCreateAllSuccessTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains("503"));

		}
	}

	@Test
	void ImmediateCreateSaque1Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/1immediateCreateSaqueTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_SAQUE_VALOR;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateSaque2Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/2immediateCreateSaqueTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_SAQUE_MODALIDADE_ALTERACAO;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateSaque3Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/3immediateCreateSaqueTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_SAQUE_MODALIDADE_AGENTE;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateSaque4Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/4immediateCreateSaqueTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_SAQUE_PRESTADOR_SERVICO_SAQUE;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateTroco1Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/1immediateCreateTrocoTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_TROCO_VALOR;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateTroco2Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/2immediateCreateTrocoTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_TROCO_MODALIDADE_ALTERACAO;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateTroco3Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/3immediateCreateTrocoTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_TROCO_MODALIDADE_AGENTE;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateTroco4Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/4immediateCreateTrocoTest.json").getFile(), ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		String expectedMessage = ImmediateCreateConstant.MSG_PROPRIEDADE_VALOR_RETIRADA_TROCO_PRESTADOR_SERVICO_SAQUE;

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains(expectedMessage));

		}
	}

	@Test
	void ImmediateCreateDoubleHeaderTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "0");
		httpHeaders.set("accountNumber", "0");
		httpHeaders.set("taxid", "0");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateAllSuccessTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains("400"));

		}
	}

	@Test
	void ImmediateCreateLocalBranchTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "0");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateAllSuccessTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains("400"));

		}
	}

	@Test
	void ImmediateCreateAccountNumberTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "0");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateAllSuccessTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains("400"));

		}
	}

	@Test
	void ImmediateCreateTaxidTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "53113791000122");
		httpHeaders.set("aliasStatus", "ACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateAllSuccessTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains("400"));

		}
	}

	@Test
	void ImmediateCreateAliasStatusTest() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "001");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "0");
		httpHeaders.set("aliasStatus", "INACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateAllSuccessTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains("400"));

		}
	}

	@Test
	void ImmediateCreateMessage1OrMessage2Test() throws IOException, URISyntaxException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		httpHeaders.set("apim_client_id", "c0b71ff6-7b89-4f1d-8d81-c79d2c149a65");
		httpHeaders.set("apim_guid", "3fa85f14-0202-4562-b3fd-2c963f66afa6");
		httpHeaders.set("localBranch", "0");
		httpHeaders.set("accountNumber", "86100355");
		httpHeaders.set("taxid", "0");
		httpHeaders.set("aliasStatus", "INACTIVE");

		ImmediateCreateRequestDTO immediateCreateRequestDTO = mapper.readValue(
				new ClassPathResource("/immediateCreateAllSuccessTest.json").getFile(),
				ImmediateCreateRequestDTO.class);

		HttpEntity<ImmediateCreateRequestDTO> immediateCreateHttpEntity = new HttpEntity<ImmediateCreateRequestDTO>(
				immediateCreateRequestDTO, httpHeaders);

		try {

			restTemplate.exchange(immediateCreate, HttpMethod.PUT, immediateCreateHttpEntity, String.class);

		} catch (Exception e) {

			Assertions.assertTrue(e.getMessage().contains("400"));

		}
	}

}
