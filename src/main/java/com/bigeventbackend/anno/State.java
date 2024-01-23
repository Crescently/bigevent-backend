package com.bigeventbackend.anno;

import com.bigeventbackend.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 自定义校验规则
 */

@Documented
@Constraint(validatedBy = {StateValidation.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface State {
    // 默认错误消息
    String message() default "状态码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
