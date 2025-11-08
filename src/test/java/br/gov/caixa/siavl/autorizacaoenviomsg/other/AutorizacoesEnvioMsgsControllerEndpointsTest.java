package br.gov.caixa.siavl.autorizacaoenviomsg.other;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import br.gov.caixa.siavl.autorizacaoenviomsg.controller.AutorizacaoEnvioMsgControllerEndpoints;

@SuppressWarnings("all")
class AutorizacaoEnvioMsgControllerEndpointsTest {

	@Test
	void testConstructorIsPrivate()
			throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor<AutorizacaoEnvioMsgControllerEndpoints> constructor = AutorizacaoEnvioMsgControllerEndpoints.class
				.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		assertThrows(InvocationTargetException.class, () -> {
			constructor.newInstance();
		});
	}
}
