package com.fitlife.app.dataclass.request.trainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssistantChatRequest {
    public String model = "gpt-3.5-turbo";
    public List<String> messages;
}
