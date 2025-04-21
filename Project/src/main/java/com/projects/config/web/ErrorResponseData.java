package com.projects.config.web;

import lombok.Data;


@Data
public class ErrorResponseData extends ResponseData {

  
    private String exceptionClazz;

    public ErrorResponseData(String message) {
        super(false, ResponseData.DEFAULT_ERROR_CODE, message, new Object());
    }

    public ErrorResponseData(Integer code, String message) {
        super(false, code, message, new Object());
    }

    public ErrorResponseData(Integer code, String message, Object object) {
        super(false, code, message, object);
    }
}
