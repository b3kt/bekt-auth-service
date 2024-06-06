package com.github.b3kt.auth.provider.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class GetAccessTokenResponse extends BaseResponse {
     private String accessToken;
     private long expiresIn;
     private long refreshExpiresIn;
     private String refreshToken;
     private String tokenType;
     private long notBeforePolicy;
     private String sessionState;
     private String scope;
}
