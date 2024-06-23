package com.github.b3kt.auth.command.request;

import com.github.b3kt.auth.common.model.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCommandRequest extends BaseRequest implements Serializable {
    private String name;
    private String email;
    private String phoneNumber;
}
