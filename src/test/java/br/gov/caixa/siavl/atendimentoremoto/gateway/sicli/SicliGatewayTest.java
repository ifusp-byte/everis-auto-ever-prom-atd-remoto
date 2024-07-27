package br.gov.caixa.siavl.atendimentoremoto.gateway.sicli;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@SuppressWarnings("all")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = { "env.apimanager.key=l7xx2b6f4c64f3774870b0b9b399a77586f5",
		"env.apimanager.url=http://localhost:9999",
		"env.url.sicli=cadastro/v2/clientes?campos=dadosbasicos,composicaoSocietaria,contratos,documentos&cpfcnpj=" })
public class SicliGatewayTest {

	/*
	public static ObjectMapper mapper;
	static WireMockServer wireMockServer;

	public void setup(int getSicliStatusReturn, String getSicliBodyReturn) throws Exception {
		mapper = new ObjectMapper();
		wireMockServer = new WireMockServer(wireMockConfig().dynamicPort().port(9999).bindAddress("localhost"));
		wireMockServer.start();
		WireMock.configureFor("localhost", wireMockServer.port());
	}

	public void teardown() throws Exception {
		wireMockServer.stop();
	}
	*/

}
