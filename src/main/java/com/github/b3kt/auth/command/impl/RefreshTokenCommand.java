package com.github.b3kt.auth.command.impl;

import com.github.b3kt.auth.command.IRefreshTokenCommand;
import com.github.b3kt.auth.command.response.LoginCommandResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.http.HttpStatus;

@ApplicationScoped
public class RefreshTokenCommand extends BaseKeycloakCommand implements IRefreshTokenCommand {
    @Override
    public Uni<LoginCommandResponse> execute(String accessToken) {
        return keycloakProvider.refreshAccessToken(accessToken)
                .map(response -> LoginCommandResponse
                        .builder()
                        .responseCode(HttpStatus.SC_OK)
                        .responseMessage("Ok")
                        .accessToken(response.getToken())
                        .refreshToken(response.getRefreshToken())
                        .build());
    }
}
