package com.due.cloud.bridge.resource.parser;

import org.jetbrains.annotations.NotNull;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 *  自定义表达式解析器
 * @author 韩文港
 */
public class DueExpressionParser implements ExpressionParser {


    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    @NotNull
    @Override
    public Expression parseExpression(@NotNull String expressionString) throws ParseException {
        return spelExpressionParser.parseExpression(expressionString);
    }

    @NotNull
    @Override
    public Expression parseExpression(@NotNull String expressionString, @NotNull ParserContext context) throws ParseException {
        return spelExpressionParser.parseExpression(expressionString, context);
    }
}
