package com.turaninarcis.group_activity_planner.utility;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.support.WebBindingInitializer;

@ControllerAdvice
public class FieldTrimmer implements WebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    
}
