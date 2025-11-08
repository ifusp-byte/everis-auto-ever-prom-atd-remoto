package br.gov.caixa.siavl.autorizacaoenviomsg;

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
