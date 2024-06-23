package com.github.b3kt.auth.command;

import com.github.b3kt.auth.command.response.LoginCommandResponse;
import com.github.b3kt.auth.common.Command;

public interface IRefreshTokenCommand extends Command<String, LoginCommandResponse> {
}
