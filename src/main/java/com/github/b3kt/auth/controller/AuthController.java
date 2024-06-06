package com.github.b3kt.auth.controller;

import com.github.b3kt.auth.command.ILoginCommand;
import com.github.b3kt.auth.command.IRegisterCommand;
import com.github.b3kt.auth.command.request.LoginCommandRequest;
import com.github.b3kt.auth.command.request.RegisterCommandRequest;
import com.github.b3kt.auth.command.response.LoginCommandResponse;
import com.github.b3kt.auth.command.response.RegisterCommandResponse;
import com.github.b3kt.auth.common.controller.BaseController;
import com.github.b3kt.auth.controller.response.LoginWebResponse;
import com.github.b3kt.auth.controller.response.RegisterWebResponse;
import com.github.b3kt.auth.exception.ProviderException;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RoutingExchange;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class AuthController extends BaseController {

    @Inject
    ILoginCommand loginCommand;

    @Inject
    IRegisterCommand registerCommand;

    @Route(path = "/api/auth/login", methods = Route.HttpMethod.POST)
    public Uni<LoginWebResponse> login(RoutingExchange routingExchange) {
        LoginCommandRequest commandRequest = toCommandRequest(routingExchange.context().body().asJsonObject(),
                LoginCommandRequest.class);
        return loginCommand.execute(commandRequest).onFailure().recoverWithItem(throwable -> {
            ProviderException exception = (ProviderException) throwable;
            return LoginCommandResponse.builder()
                    .responseCode(exception.getResponseCode())
                    .responseMessage(exception.getResponseMessage())
                    .build();
        }).map(commandResponse -> objectMapper.convertValue(commandResponse, LoginWebResponse.class));
    }

    @Route(path = "/api/auth/register", methods = Route.HttpMethod.POST)
    public Uni<RegisterWebResponse> register(RoutingExchange routingExchange) {
        RegisterCommandRequest commandRequest = toCommandRequest(routingExchange.context().body().asJsonObject(),
                RegisterCommandRequest.class);
        return registerCommand.execute(commandRequest).onFailure().recoverWithItem(throwable -> {
            ProviderException exception = (ProviderException) throwable;
            return RegisterCommandResponse.builder()
                    .responseCode(exception.getResponseCode())
                    .responseMessage(exception.getResponseMessage())
                    .build();
        }).map(commandResponse -> objectMapper.convertValue(commandResponse, RegisterWebResponse.class));
    }
}
