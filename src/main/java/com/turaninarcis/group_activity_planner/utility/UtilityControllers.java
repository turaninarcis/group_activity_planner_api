package com.turaninarcis.group_activity_planner.utility;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

public class UtilityControllers {
    
    public static String getErrors(BindingResult result){
        List<String> errorList = result.getAllErrors()
                                    .stream()
                                    .map(error -> error.getDefaultMessage()) // Get the default validation message
                                    .collect(Collectors.toList());
        return String.join(", ", errorList);
    }
}
