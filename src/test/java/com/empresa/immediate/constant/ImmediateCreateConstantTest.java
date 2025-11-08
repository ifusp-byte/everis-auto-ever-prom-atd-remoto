package com.empresa.immediate.constant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import com.empresa.immediate.util.constant.ImmediateCreateConstant;

class ImmediateCreateConstantTest {

	@Test
	void testConstructorIsPrivate() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
		Constructor<ImmediateCreateConstant> constructor = ImmediateCreateConstant.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		assertThrows(InvocationTargetException.class, () -> {
			
			constructor.newInstance();
		});
	}

}
