package com.github.b3kt.auth.command;

import com.github.b3kt.auth.command.request.RegisterCommandRequest;
import com.github.b3kt.auth.command.response.RegisterCommandResponse;
import com.github.b3kt.auth.common.Command;

public interface IRegisterCommand extends Command<RegisterCommandRequest, RegisterCommandResponse> {
}
