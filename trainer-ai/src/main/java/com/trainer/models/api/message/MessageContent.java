package com.trainer.models.api.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trainer.models.api.message.content.ImageFile;
import com.trainer.models.api.message.content.Text;

public record MessageContent(
        String type,
        Text text,
        @JsonProperty("image_file")
        ImageFile imageFile
) {}
