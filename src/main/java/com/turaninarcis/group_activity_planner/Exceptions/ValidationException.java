package com.turaninarcis.group_activity_planner.Exceptions;

import org.springframework.validation.BindingResult;

import com.turaninarcis.group_activity_planner.utility.UtilityControllers;

public class ValidationException extends RuntimeException {
    public ValidationException(BindingResult errorList){
        super(UtilityControllers.getErrors(errorList));
    }
}
