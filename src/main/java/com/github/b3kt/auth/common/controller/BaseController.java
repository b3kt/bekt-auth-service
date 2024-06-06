package com.github.b3kt.auth.common.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.b3kt.auth.common.model.BaseRequest;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;

public class BaseController {
    @Inject
    protected ObjectMapper objectMapper;

    protected <R extends BaseRequest> R toCommandRequest(JsonObject webRequest,
                                                             Class<R> commandClass){
        return objectMapper.convertValue(webRequest, commandClass);
    }
}
