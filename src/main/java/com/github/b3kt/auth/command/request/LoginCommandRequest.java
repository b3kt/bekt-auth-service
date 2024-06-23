package com.github.b3kt.auth.command.request;

import com.github.b3kt.auth.common.model.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.io.Serializable;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class LoginCommandRequest extends BaseRequest implements Serializable {
    private String username;
    private String password;
    private boolean keepSession;
}
