package com.github.b3kt.auth.command.impl;

import com.github.b3kt.auth.command.ILoginCommand;
import com.github.b3kt.auth.command.request.LoginCommandRequest;
import com.github.b3kt.auth.command.response.LoginCommandResponse;
import com.github.b3kt.auth.common.command.AbstractCommand;
import com.github.b3kt.auth.provider.IKeycloakProvider;
import com.github.b3kt.auth.provider.model.GetAccessTokenRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.http.HttpStatus;


@ApplicationScoped
public class LoginCommand extends AbstractCommand implements ILoginCommand {

    @Inject
    IKeycloakProvider keycloakProvider;

    @Override
    public Uni<LoginCommandResponse> execute(LoginCommandRequest loginCommandRequest) {

        GetAccessTokenRequest request = GetAccessTokenRequest.builder()
                .username(loginCommandRequest.getUsername())
                .password(loginCommandRequest.getPassword())
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(grantType)
                .build();

        return keycloakProvider.getAccessToken(request)
                .map(accessToken -> LoginCommandResponse
                        .builder()
                        .responseCode(HttpStatus.SC_OK)
                        .responseMessage("Ok")
                        .token(accessToken.getToken())
                        .build());
    }
}
