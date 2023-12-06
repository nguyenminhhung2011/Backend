package com.trainer.models.api.completion;

import com.trainer.models.common.Usage;
import lombok.Data;
import java.util.List;

@Data
public class CompletionResult {
    String id;
    String object;
    long created;
    String model;
    List<CompletionChoice> choices;
    Usage usage;
}
