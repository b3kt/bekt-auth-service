package com.github.b3kt.auth.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.b3kt.auth.common.model.BaseRequest;
import com.github.b3kt.auth.exception.ValidationException;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.quarkus.vertx.web.RoutingExchange;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.CompletableFuture;

public class BaseController {
    @Inject
    protected ObjectMapper objectMapper;

    protected <R extends BaseRequest> R toCommandRequest(JsonObject webRequest,
                                                             Class<R> commandClass){
        return objectMapper.convertValue(webRequest, commandClass);
    }

    protected CompletableFuture<String> validateAccessToken(RoutingExchange routingExchange){
        CompletableFuture<String> future = new CompletableFuture<>();
        try{
            future.complete( routingExchange
                    .getHeader(HttpHeaderNames.AUTHORIZATION)
                    .filter(value -> StringUtils.isNotBlank(value.replace("Bearer ", "")))
                    .orElseThrow(() -> new ValidationException("accessToken","must not be empty")));
        }catch (ValidationException ex){
            future.completeExceptionally(ex);
        }
        return future;
    }
}
