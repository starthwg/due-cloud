package com.due.cloud.bridge.resource.parser;

import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

public class DueWebDueSecurityExpressionHandler extends DefaultWebSecurityExpressionHandler {

    public DueWebDueSecurityExpressionHandler() {
        this.setExpressionParser(new DueExpressionParser());
    }

    @Override
    protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication, FilterInvocation invocation) {
        StandardEvaluationContext evaluationContextInternal = super.createEvaluationContextInternal(authentication, invocation);
        evaluationContextInternal.setVariable("due", authentication.getAuthorities());
        return evaluationContextInternal;
    }

}
