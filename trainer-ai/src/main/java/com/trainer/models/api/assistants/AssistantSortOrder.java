package com.trainer.models.api.assistants;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AssistantSortOrder {

    @JsonProperty("asc")
    ASC,

    @JsonProperty("desc")
    DESC
}
