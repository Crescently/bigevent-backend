package com.bigeventbackend.validation;

import com.bigeventbackend.anno.State;
import com.bigeventbackend.constant.ArticleStateConstant;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 自定义校验
 */
public class StateValidation implements ConstraintValidator<State, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.equals(ArticleStateConstant.DRAFT) || value.equals(ArticleStateConstant.PUBLISHED);
    }
}
