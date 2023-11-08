package com.FitnessApp.DTO;

import com.FitnessApp.Model.Steps;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InstructionsDeserialize extends StdDeserializer<List<Steps>> {

    public InstructionsDeserialize(){
        this(null);
    }

    protected InstructionsDeserialize(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<Steps> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        List<Steps> result = new ArrayList<>();

        node.forEach(jsonNode -> {
            result.add(new Steps(jsonNode.asText("")));
        });

        return result;
    }
}
