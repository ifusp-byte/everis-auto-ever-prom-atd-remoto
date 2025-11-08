package br.gov.caixa.siavl.atendimentoremoto;

import static br.gov.caixa.siavl.atendimentoremoto.util.ConstantsUtils.WARNINGS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import com.microsoft.applicationinsights.attach.ApplicationInsights;

@SpringBootApplication
@SuppressWarnings("all")
@PropertySource("classpath:application-app.properties")
public class RunApplication {

	public static void main(String[] args) {
		ApplicationInsights.attach();
		SpringApplication.run(RunApplication.class, args);
	}

}
