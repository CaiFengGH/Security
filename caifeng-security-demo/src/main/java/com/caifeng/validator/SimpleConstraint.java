package com.caifeng.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SimpleConstraintValidator.class)
public @interface SimpleConstraint {
	
	String message();

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
