package br.gov.caixa.siavl.atendimentoremoto.controller.report;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import br.gov.caixa.siavl.atendimentoremoto.report.dto.ReportInputDTO;

@SuppressWarnings("all")
class AtendimentoRemotoControllerReportTest extends ControllerTest {

	void relatorio(String relatorioInpuDtoFile) throws StreamReadException, DatabindException, IOException {

		String BASE_URL = atdremotoUrl + "/relatorios";
		ReportInputDTO reportInputDTO = mapper.readValue(
				new ClassPathResource("/relatorio/" + relatorioInpuDtoFile + ".json").getFile(), ReportInputDTO.class);

		try {

			ResponseEntity<Object> response = restTemplate.exchange(BASE_URL, HttpMethod.POST,
					newRequestEntity(reportInputDTO), Object.class);

		} catch (Exception e) {
			
			Assertions.assertTrue(e.getMessage().toString().contains("application/octet-stream"));
			
		}

	}

}
