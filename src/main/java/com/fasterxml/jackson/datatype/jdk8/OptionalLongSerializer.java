package com.fasterxml.jackson.datatype.jdk8;

import java.io.IOException;
import java.util.OptionalLong;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

final class OptionalLongSerializer extends StdSerializer<OptionalLong>
{
    private static final long serialVersionUID = 1L;

    static final OptionalLongSerializer INSTANCE = new OptionalLongSerializer();

    public OptionalLongSerializer() {
        super(OptionalLong.class);
    }

    // @since 2.6
    @Override
    public boolean isEmpty(SerializerProvider provider, OptionalLong value) {
        return (value == null) || !value.isPresent();
    }
    
    @Override
    public void serialize(OptionalLong value, JsonGenerator jgen, SerializerProvider provider)
    throws IOException
    {
        if (value.isPresent()) {
            jgen.writeNumber(value.getAsLong());
        } else { // should we get here?
            jgen.writeNull();
        }
    }
}
