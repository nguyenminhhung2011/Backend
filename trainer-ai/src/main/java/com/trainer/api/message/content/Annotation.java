package com.trainer.api.message.content;


import com.fasterxml.jackson.annotation.JsonProperty;

public record Annotation(String type, String text,
                         @JsonProperty("file_citation")
                         FileCitation fileCitation,
                         @JsonProperty("file_path")
                         FilePath filePath,
                         @JsonProperty("start_index")
                         int startIndex,
                         @JsonProperty("end_index")
                         int endIndex) {
}
