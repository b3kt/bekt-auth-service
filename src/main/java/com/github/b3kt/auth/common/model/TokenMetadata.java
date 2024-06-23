package com.github.b3kt.auth.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenMetadata {
    private long exp;
    private long iat;
    private String jti;
    private String iss;
    private String sub;
    private String typ;
    private String azp;
    private String sessionState;
    private String acr;
    private ResourceAccess resourceAccess;
    private String scope;
    private String sid;
    private boolean emailVerified;
    private String name;
    private String preferredUsername;
    private String givenName;
    private String familyName;
    private String email;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    protected static class ResourceAccess{
        private Account account;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    protected static class Account{
        private List<String> roles;
    }
}
