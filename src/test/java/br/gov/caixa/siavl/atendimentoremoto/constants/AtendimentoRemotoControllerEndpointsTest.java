package br.gov.caixa.siavl.atendimentoremoto.constants;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import br.gov.caixa.siavl.atendimentoremoto.controller.AtendimentoRemotoControllerEndpoints;

@SuppressWarnings("all")
class AtendimentoRemotoControllerEndpointsTest {

	@Test
	void testConstructorIsPrivate()
			throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor<AtendimentoRemotoControllerEndpoints> constructor = AtendimentoRemotoControllerEndpoints.class
				.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		assertThrows(InvocationTargetException.class, () -> {
			constructor.newInstance();
		});
	}
}
