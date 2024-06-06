package br.gov.caixa.siavl.atendimentoremoto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.microsoft.applicationinsights.attach.ApplicationInsights;

@SpringBootApplication
public class RunApplication {

	public static void main(String[] args) {
		ApplicationInsights.attach();
		SpringApplication.run(RunApplication.class, args);
	}

}
