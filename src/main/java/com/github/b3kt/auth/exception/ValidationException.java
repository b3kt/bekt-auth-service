package com.github.b3kt.auth.exception;

import jakarta.ws.rs.WebApplicationException;
import lombok.*;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationException extends WebApplicationException {
    private int responseCode;
    private String responseMessage;
    private Map<String, String> errors;

    public ValidationException(String field, String message) {
        this.responseCode = HttpStatus.SC_BAD_REQUEST;
        this.responseMessage = message;
        this.errors = new HashMap<>(){{
            put(field, message);
        }};
    }
}
