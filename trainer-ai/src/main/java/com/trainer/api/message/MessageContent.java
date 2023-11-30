package com.trainer.api.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trainer.api.message.content.FileCitation;
import com.trainer.api.message.content.FilePath;
import com.trainer.api.message.content.ImageFile;
import com.trainer.api.message.content.Text;

public record MessageContent(
        String type,

        Text text,

        @JsonProperty("image_file")
        ImageFile imageFile
) {}
