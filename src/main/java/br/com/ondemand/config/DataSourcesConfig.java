package br.com.ondemand.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourcesConfig {

	private final AwsSecretsDto awsSecrets;

	public DataSourcesConfig(AwsSecretsDto awsSecrets) {
		this.awsSecrets = awsSecrets;
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().url(awsSecrets.getDatasourceUrl())
				.username(awsSecrets.getDatasourceUsername()).password(awsSecrets.getDatasourcePassword()).build();
	}
}
