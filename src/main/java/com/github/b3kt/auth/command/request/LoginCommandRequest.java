package com.github.b3kt.auth.command.request;

import com.github.b3kt.auth.common.model.BaseRequest;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class LoginCommandRequest extends BaseRequest {
    private String username;
    private String password;
    private boolean keepSession;
}
