package com.exigen.ldap.type;

import org.springframework.http.HttpStatus;

/**
 * Generic web service response object, if needed
 */
public class GenericResponse {

    private String status = String.valueOf(HttpStatus.OK.value());

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private GenericResponseEntity entity;

    public GenericResponseEntity getEntity() {
        return entity;
    }

    public void setEntity(GenericResponseEntity entity) {
        this.entity = entity;
    }

}