package com.trainer.api.assistants;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Assistant {
    String id;

    String object;

    @JsonProperty("created_at")
    Integer createdAt;

    String name;

    String description;

    @NonNull
    String model;

    String instructions;

    List<Tool> tools;

    @JsonProperty("file_ids")
    List<String> fileIds;

    Map<String,String> metadata;
}
