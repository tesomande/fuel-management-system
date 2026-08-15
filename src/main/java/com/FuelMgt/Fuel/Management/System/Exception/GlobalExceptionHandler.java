package com.FuelMgt.Fuel.Management.System.Exception;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // Handles Bean Validation errors
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleValidationException(
            ConstraintViolationException ex) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setType(
            URI.create(
            "https://fuel-management-system/errors/validation")
        );

        problem.setTitle("Validation Error");
        problem.setDetail(
            "One or more fields failed validation"
        );


        Map<String,String> errors = new HashMap<>();

        ex.getConstraintViolations()
          .forEach(error -> {

              errors.put(
                  error.getPropertyPath().toString(),
                  error.getMessage()
              );

          });


        problem.setProperty("errors", errors);

        return problem;
    }


    // Handles Service Layer validation errors
    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(
            IllegalArgumentException ex) {


        ProblemDetail problem =
                ProblemDetail.forStatus(
                        HttpStatus.BAD_REQUEST);


        problem.setType(
            URI.create(
            "https://fuel-management-system/errors/business-validation")
        );


        problem.setTitle("Validation Error");

        problem.setDetail(ex.getMessage());


        return problem;
    }

}