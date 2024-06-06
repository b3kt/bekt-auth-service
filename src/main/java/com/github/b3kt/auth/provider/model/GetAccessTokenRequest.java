package com.github.b3kt.auth.provider.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAccessTokenRequest {
    private String grantType;
    private String clientId;
    private String clientSecret;
    private String username;
    private String password;
}
