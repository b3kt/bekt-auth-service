package com.github.b3kt.auth.configuration.properties;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Data
public class KeycloakProperties {

    @ConfigProperty(name = "app.auth.auth-base-url")
    String authBaseUrl;

    @ConfigProperty(name = "app.auth.realm")
    String realm;

    @ConfigProperty(name = "app.auth.admin-username")
    String adminUsername;

    @ConfigProperty(name = "app.auth.admin-password")
    String adminPassword;

    @ConfigProperty(name = "app.auth.client-id")
    String clientId;

    @ConfigProperty(name = "app.auth.admin-client-id")
    String adminClientId;

    @ConfigProperty(name = "app.auth.client-secret")
    String clientSecret;

}
