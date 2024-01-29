package com.fitlife.app.dataClass.dto.trainer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatThreadDto {
    public UUID id;
    public String title;
    public List<ChatDto> chats;
}
