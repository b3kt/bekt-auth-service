package com.github.b3kt.auth.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginWebRequest {
    private String username;
    private String password;
    private boolean rememberMe;
}
