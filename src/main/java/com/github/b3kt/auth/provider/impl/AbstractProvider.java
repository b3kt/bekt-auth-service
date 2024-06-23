package com.github.b3kt.auth.provider.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.b3kt.auth.common.helper.ValidationHelper;
import com.github.b3kt.auth.provider.model.BaseResponse;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;

public abstract class AbstractProvider<R extends BaseResponse> {

    @Inject
    protected ObjectMapper objectMapper;

    @Inject
    protected ValidationHelper validationHelper;

    public R toResponse(JsonObject response){
        return objectMapper.convertValue(response, new TypeReference<>() {});
    }
}
