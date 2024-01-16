package com.due.cloud.bridge.resource;

import com.due.basic.tookit.oauth.AbstractDueAuthentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class MobileAuthenticated extends AbstractDueAuthentication {



    @Override
    public String getName() {
        return Optional.ofNullable(this.getDueBasicUser()).map(UserDetails::getUsername).orElse(null);
    }
}
