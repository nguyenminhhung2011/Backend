package com.fitlife.app.DTO.DataClass;

import com.fitlife.app.Model.Exercise.Steps;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InstructionsDeserialize extends StdDeserializer<List<Steps>> {

    public InstructionsDeserialize(){
        this(null);
    }

    protected InstructionsDeserialize(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<Steps> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        List<Steps> result = new ArrayList<>();
        AtomicInteger step = new AtomicInteger();
        node.forEach(jsonNode -> {
            result.add(new Steps(step.getAndIncrement(),jsonNode.asText("")));
        });

        return result;
    }
}
