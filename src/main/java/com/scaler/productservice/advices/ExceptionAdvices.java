package com.scaler.productservice.advices;

import com.scaler.productservice.dtos.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionAdvices {

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleException(RuntimeException e){
        ErrorResponse err = new ErrorResponse();
        err.setStatus("ERROR");
        err.setMessage("Runtime exceptIon: Something went wrong");

        return  err;
    }
}
