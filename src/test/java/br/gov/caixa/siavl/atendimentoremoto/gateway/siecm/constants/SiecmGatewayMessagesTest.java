package br.gov.caixa.siavl.atendimentoremoto.gateway.siecm.constants;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

@SuppressWarnings("all")

class SiecmGatewayMessagesTest {

	@Test
	void testConstructorIsPrivate()
			throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor<SiecmGatewayMessages> constructor = SiecmGatewayMessages.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		assertThrows(InvocationTargetException.class, () -> {
			constructor.newInstance();
		});
	}
}
