package com.fitlife.app.DTO.Request.Trainer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    public UUID id;
    public String message;
    public String role;
}
