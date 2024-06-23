package com.github.b3kt.auth.command.response;

import com.github.b3kt.auth.common.model.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginCommandResponse extends BaseResponse implements Serializable {
    private String accessToken;
    private String refreshToken;
    private String redirectUrl;
}
