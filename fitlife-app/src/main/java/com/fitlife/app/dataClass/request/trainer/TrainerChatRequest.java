package com.fitlife.app.dataClass.request.trainer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerChatRequest {
    public String trainerId;
    public String message;
}
