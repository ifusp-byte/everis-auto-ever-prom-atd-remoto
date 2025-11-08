package br.com.ondemand;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EntityScan
@ComponentScan
@EnableWorkflow
@EnableScheduling
@EnableJpaAuditing
@ServiceApplication
@EnableServiceSecurity
@EnableJpaRepositories
@EnableBatchProcessing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean(name = "onDemandFilterRepository")
	public FilterRepository filterRepositoryJpa() {
		return new FilterRepositoryJpa();
	}
}
