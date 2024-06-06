package com.github.b3kt.auth.command.response;

import com.github.b3kt.auth.common.model.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class LoginCommandResponse extends BaseResponse {
    private String token;
    private String redirectUrl;
}
