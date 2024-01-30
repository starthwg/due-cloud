package com.due.basic.tookit.serializer;


import com.due.basic.tookit.annotation.BigDecimalFormat;
import com.due.basic.tookit.enums.EnumBigDecimalFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

public class BigDecimalSerializer extends JsonSerializer<BigDecimal> implements ContextualSerializer {
    private EnumBigDecimalFormat format;
    private int digits;
    private int roundingMode;

    @Override
    public void serialize(BigDecimal value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(format.getDesensitizer().apply(value).setScale(digits, roundingMode));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        BigDecimalFormat annotation = beanProperty.getAnnotation(BigDecimalFormat.class);
        if (Objects.nonNull(annotation) && Objects.equals(BigDecimal.class, beanProperty.getType().getRawClass())) {
            this.format = annotation.format();
            this.digits = annotation.digits();
            this.roundingMode = annotation.roundingMode();
            return this;
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }
}
