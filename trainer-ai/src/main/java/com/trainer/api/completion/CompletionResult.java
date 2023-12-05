package com.trainer.api.completion;

import com.trainer.api.Usage;
import lombok.Data;
import java.util.List;

@Data
public class CompletionResult {
    String id;
    String object;
    long created;
    String model;
    List<LogProbResult> choices;
    Usage usage;
}
