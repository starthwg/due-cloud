package com.cloud.bridge.auth.select;

import com.cloud.bridge.auth.convert.authentication.RequestTokenAuthenticationConvert;

public class DueRequestTokenBackCodeConvertImportselector extends DueImportSelector {
    @Override
    protected Class<?> getSpringFactoriesLoaderFactoryClass() {
        return RequestTokenAuthenticationConvert.class;
    }
}
