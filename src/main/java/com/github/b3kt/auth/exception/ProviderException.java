package com.github.b3kt.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProviderException extends ClientWebApplicationException {
    private int responseCode;
    private String responseMessage;

    public ProviderException(Throwable throwable) {
        Optional.ofNullable((ClientWebApplicationException) throwable)
            .ifPresent(exception -> {
                this.responseCode = exception.getResponse().getStatus();
                this.responseMessage = exception.getResponse().getStatusInfo().getReasonPhrase();
            });
    }
}
