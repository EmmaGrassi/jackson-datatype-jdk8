package com.fasterxml.jackson.datatype.jdk8;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;

class Jdk8Deserializers extends Deserializers.Base
{
    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc)
    throws JsonMappingException
    {
        final Class<?> raw = type.getRawClass();
        if (raw == Optional.class) {
            JavaType[] types = config.getTypeFactory().findTypeParameters(type, Optional.class);
            JavaType refType = (types == null) ? TypeFactory.unknownType() : types[0];
            JsonDeserializer<?> valueDeser = type.getValueHandler();
            TypeDeserializer typeDeser = type.getTypeHandler();
            // [jackson-datatype-guava:Issue#42]: Polymorphic types need type deserializer
            if (typeDeser == null) {
                typeDeser = config.findTypeDeserializer(refType);
            }
            return new OptionalDeserializer(type, refType, typeDeser, valueDeser);
        }
        if (raw == OptionalInt.class) {
            return OptionalIntDeserializer.INSTANCE;
        }
        if (raw == OptionalLong.class) {
            return OptionalLongDeserializer.INSTANCE;
        }
        if (raw == OptionalDouble.class) {
            return OptionalDoubleDeserializer.INSTANCE;
        }
        return super.findBeanDeserializer(type, config, beanDesc);
    }
}
