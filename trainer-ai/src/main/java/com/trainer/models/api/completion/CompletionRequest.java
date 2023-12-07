package com.trainer.models.api.completion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletionRequest {
    private String model;
    private String prompt;
    private String suffix;

    @JsonProperty("max_tokens")
    private Integer maxTokens;

    private Double temperature;

    @JsonProperty("top_p")
    private Double topP;

    private Integer n;
    private Boolean stream;
    private Integer logprobs;
    private Boolean echo;
    private List<String> stop;


    @JsonProperty("presence_penalty")
    private Double presencePenalty;

    @JsonProperty("frequency_penalty")
    private Double frequencyPenalty;

    @JsonProperty("best_of")
    private Integer bestOf;

    @JsonProperty("logit_bias")
    private Map<String,Integer> logitBias;
    private String user;
}
