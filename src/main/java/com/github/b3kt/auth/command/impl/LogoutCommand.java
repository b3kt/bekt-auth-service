package com.github.b3kt.auth.command.impl;

import com.github.b3kt.auth.command.ILogoutCommand;
import com.github.b3kt.auth.command.request.VoidRequest;
import com.github.b3kt.auth.command.response.BooleanResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.http.HttpStatus;

@ApplicationScoped
public class LogoutCommand extends BaseKeycloakCommand implements ILogoutCommand {
    @Override
    public Uni<BooleanResponse> execute(VoidRequest request) {
        return keycloakProvider.logout()
                .onItem()
                .transform(result -> BooleanResponse.builder()
                    .result(Boolean.TRUE)
                    .responseCode(HttpStatus.SC_OK)
                    .responseMessage("logout success")
                    .success(true)
                    .build());
    }
}
