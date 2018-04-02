package com.github.tumerbaatar.storage.model.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.tumerbaatar.storage.model.StockEntry;

import java.io.IOException;
import java.util.List;

public class StockEntriesSerializer extends StdSerializer<List<StockEntry>> {

    public StockEntriesSerializer() {
        this(null);
    }

    public StockEntriesSerializer(Class<List<StockEntry>> t) {
        super(t);
    }

    @Override
    public void serialize(List<StockEntry> items, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (StockEntry entry : items) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", entry.getId());
            jsonGenerator.writeNumberField("part", entry.getPart().getId());
            jsonGenerator.writeNumberField("box", entry.getBox().getId());
            jsonGenerator.writeStringField("creationTimestamp", entry.getCreationTimestamp().toString());
            jsonGenerator.writeStringField("updateTimestamp", entry.getUpdateTimestamp().toString());
            jsonGenerator.writeStringField("comment", entry.getComment());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();

    }
}
