package com.github.b3kt.auth.command.impl;

import com.github.b3kt.auth.common.command.AbstractCommand;
import com.github.b3kt.auth.provider.IKeycloakProvider;
import jakarta.inject.Inject;

public class BaseKeycloakCommand extends AbstractCommand {
    @Inject
    IKeycloakProvider keycloakProvider;
}
