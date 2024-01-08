package com.fitlife.app.dataclass.request.trainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatThreadRequest {
    public UUID id;
    public String title;
}
