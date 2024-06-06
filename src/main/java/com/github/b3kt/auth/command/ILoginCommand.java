package com.github.b3kt.auth.command;

import com.github.b3kt.auth.command.request.LoginCommandRequest;
import com.github.b3kt.auth.command.response.LoginCommandResponse;
import com.github.b3kt.auth.common.Command;

public interface ILoginCommand extends Command<LoginCommandRequest, LoginCommandResponse> {

}
