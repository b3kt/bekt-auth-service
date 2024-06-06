package com.github.b3kt.auth.common.helper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Validator;

@ApplicationScoped
public class ValidationHelper {
    @Inject
    private Validator validator;

    public <R> R validate(R requestModel){
        try{
            validator.validate(requestModel);
        }catch (Exception ex){
            return null;
        }
        return requestModel;
    }
}
