package com.empresa.immediate.util.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

import com.empresa.immediate.util.annotation.ValorDTOAnnotation.List;
import com.empresa.immediate.util.annotation.impl.ValorDTOAnnotationImpl;
import com.empresa.immediate.util.constant.ImmediateCreateConstant;

@Documented
@Retention(RUNTIME)
@Repeatable(List.class)
@ReportAsSingleViolation
@Constraint(validatedBy = {ValorDTOAnnotationImpl.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
public @interface ValorDTOAnnotation {
	
	String message() default ImmediateCreateConstant.BODY_VALOR_ORIGINAL;
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		
		ValorDTOAnnotation[] value();
		
	}
	
}
