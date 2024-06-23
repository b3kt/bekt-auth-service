package com.github.b3kt.auth.exception;

import lombok.*;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
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
