package com.trainer.api.message.content;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FilePath(@JsonProperty("file_id") String fileId) {
}
