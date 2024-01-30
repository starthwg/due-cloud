package com.cloud.bridge.auth.selector;


import com.cloud.bridge.auth.provider.DueAuthenticationProvider;

public class DueAuthenticationProviderSelector extends DueImportSelector {
    @Override
    protected Class<?> getSpringFactoriesLoaderFactoryClass() {
        return DueAuthenticationProvider.class;
    }
}
