package com.github.b3kt.auth.common;

import io.smallrye.mutiny.Uni;

public interface Command<R1, R2> {
    Uni<R2> execute(R1 request);
}
