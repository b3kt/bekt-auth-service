package com.github.b3kt.auth.controller.response;

import com.github.b3kt.auth.common.model.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginWebResponse extends BaseResponse {
    private String accessToken;
    private String refreshToken;
    private String redirectUrl;
}
