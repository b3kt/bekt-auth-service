package com.github.b3kt.auth.command.request;

import com.github.b3kt.auth.common.model.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCommandRequest extends BaseRequest {
    private String name;
    private String email;
    private String phoneNumber;
}
