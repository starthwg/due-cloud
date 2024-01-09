package com.cloud.bridge.auth.select;

import com.cloud.bridge.auth.convert.user.GrantTypeUsernameCovert;

/**
 * grantType转detailUser
 *
 * @author hanwengang
 */
public class DueGrantTypeConvertImportSelector extends DueImportSelector {
    @Override
    protected Class<?> getSpringFactoriesLoaderFactoryClass() {
        return GrantTypeUsernameCovert.class;
    }
}
