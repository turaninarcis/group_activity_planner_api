package com.turaninarcis.group_activity_planner.utility;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CreateResponseEntity {
    public static ResponseEntity<Map<String,String>> okEntity(String message){
        return ResponseEntity.ok().body(Collections.singletonMap("message", message));
    }
    public static ResponseEntity<Map<String,String>> createdEntity(String message){
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", message));
    }
    public static ResponseEntity<Map<String,String>> badEntity(String message){
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", message));
    }
}
