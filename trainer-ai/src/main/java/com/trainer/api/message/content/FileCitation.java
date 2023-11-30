package com.trainer.api.message.content;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FileCitation(@JsonProperty("file_id") String fileId, String quote) {
}
