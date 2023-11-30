package com.trainer.api.message.content;

import java.util.List;

public record Text(String value, List<Annotation> annotations) {
}
