package com.github.b3kt.auth.command.impl;

import com.github.b3kt.auth.command.IRegisterCommand;
import com.github.b3kt.auth.command.request.RegisterCommandRequest;
import com.github.b3kt.auth.command.response.RegisterCommandResponse;
import com.github.b3kt.auth.common.command.AbstractCommand;
import com.github.b3kt.auth.provider.IKeycloakProvider;
import com.github.b3kt.auth.provider.model.CreateUserRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.http.HttpStatus;

@ApplicationScoped
public class RegisterCommand extends AbstractCommand implements IRegisterCommand {

    @Inject
    IKeycloakProvider keycloakProvider;

    @Override
    public Uni<RegisterCommandResponse> execute(RegisterCommandRequest request) {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .email(request.getEmail())
                .fullName(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .build();

        return keycloakProvider.createUser(createUserRequest)
                .onItem().ifNotNull()
                .transform(response -> RegisterCommandResponse.builder()
                            .responseCode(HttpStatus.SC_OK)
                            .responseMessage("Ok")
                            .build());
    }
}
