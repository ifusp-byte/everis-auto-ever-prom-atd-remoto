package com.empresa.immediate.util.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.empresa.immediate.dto.ValorDTO;
import com.empresa.immediate.util.annotation.ValorDTOAnnotation;
import com.empresa.immediate.util.constant.ImmediateCreateConstant;

public class ValorDTOAnnotationImpl implements ConstraintValidator<ValorDTOAnnotation, ValorDTO>{
	
	@Override 
	public boolean isValid(ValorDTO valorDTO, ConstraintValidatorContext context) {
		
		var status = false;
		
		if((valorDTO.getOriginal() != null) && (!valorDTO.getOriginal().isEmpty())) {
			
			if((Double.parseDouble(valorDTO.getOriginal()) == 0) && (valorDTO.getModalidadeAlteracao() == 0)) {
				
				context.disableDefaultConstraintViolation();
				
				context.buildConstraintViolationWithTemplate(ImmediateCreateConstant.BODY_VALOR_ORIGINAL).addConstraintViolation();
				
				status = true;
			}
			
			status = !status;
		}
		
		return status;
	}

}
