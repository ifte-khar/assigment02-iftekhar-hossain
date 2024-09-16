package com.example.carrentalgroupd.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private final String name;
    private final String field;
    private final Object value;
    public ResourceNotFoundException(String name, String field, Object value){
        super(String.format("%s not found with %s : '%s'", name, field, value));
        this.name = name;
        this.field = field;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}
