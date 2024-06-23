package com.github.b3kt.auth.common.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

@Slf4j
public abstract class AbstractCommand {

    @ConfigProperty(name = "app.auth.client-id")
    protected String clientId;

    @ConfigProperty(name = "app.auth.client-secret")
    protected String clientSecret;

    @ConfigProperty(name = "app.auth.grant-type")
    protected String grantType;
}
