package br.gov.caixa.siavl.autorizacaoenviomsg.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@SuppressWarnings("all")
public class MessageSourceConfig {

	private static final String CLASSPATH_MESSAGES = "classpath:messages";
	private static final String DEFAULT_ENCODING_UTF8 = "UTF-8";

	@Bean(name = "messageSource")
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(CLASSPATH_MESSAGES);
		messageSource.setDefaultEncoding(DEFAULT_ENCODING_UTF8);
		return messageSource;

	}

}