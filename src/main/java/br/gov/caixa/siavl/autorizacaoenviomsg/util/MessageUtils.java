package br.gov.caixa.siavl.autorizacaoenviomsg.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import br.gov.caixa.siavl.autorizacaoenviomsg.config.MessageSourceConfig;

@Component
@SuppressWarnings("all")
public class MessageUtils {

	private static final MessageSourceConfig messageSourceConfig = new MessageSourceConfig();

	public static String getMessage(String key) {
		return messageSourceConfig.messageSource().getMessage(key, null, LocaleContextHolder.getLocale());
	}

	public static String getMessage(String key, Object... args) {
		return messageSourceConfig.messageSource().getMessage(key, args, LocaleContextHolder.getLocale());
	}

}
