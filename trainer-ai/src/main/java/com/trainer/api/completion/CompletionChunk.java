package com.trainer.api.completion;

import lombok.Data;
import java.util.List;

@Data
public class CompletionChunk {
    private String id;
    private String object;

    private long created;
    private String model;
    private List<LogProbResult> choices;
}
