package com.github.b3kt.auth.command.response;

import com.github.b3kt.auth.common.model.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BooleanResponse extends BaseResponse implements Serializable {
    private boolean result;
}
