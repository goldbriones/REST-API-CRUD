package com.accenture.RESTAPICRUD.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

        private static final long serialVersionUID = 1L;

        public ResourceNotFoundException(String message){

            super(message);
        }
    }

