package com.github.b3kt.auth.properties;

import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperties;

@Data
@ConfigProperties(prefix = "app.auth")
public class AuthProperties {
    private String authBaseUrl;
    private Integer authPort;
    private String getAccessTokenPath;
    private String grantType;
    private String clientId;
}
