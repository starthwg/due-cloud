package com.cloud.bridge.auth.selector;

import com.cloud.bridge.auth.convert.authentication.RequestTokenAuthenticationConvert;

public class DueRequestTokenBackCodeConvertImportselector extends DueImportSelector {
    @Override
    protected Class<?> getSpringFactoriesLoaderFactoryClass() {
        return RequestTokenAuthenticationConvert.class;
    }
}
