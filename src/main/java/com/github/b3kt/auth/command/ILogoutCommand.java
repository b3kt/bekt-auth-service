package com.github.b3kt.auth.command;

import com.github.b3kt.auth.command.request.VoidRequest;
import com.github.b3kt.auth.command.response.BooleanResponse;
import com.github.b3kt.auth.common.Command;

public interface ILogoutCommand extends Command<VoidRequest, BooleanResponse> {
}
