package br.gov.caixa.siavl.atendimentoremoto.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

@SuppressWarnings("all")
class ConstantsUtilsTest {

	@Test
	void testConstructorIsPrivate()
			throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor<ConstantsUtils> constructor = ConstantsUtils.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		assertThrows(InvocationTargetException.class, () -> {
			constructor.newInstance();
		});
	}
}
