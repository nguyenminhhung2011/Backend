package com.fitlife.app.Utils.Mapper.trainer;

import com.fitlife.app.DTO.Request.Trainer.ChatDto;
import com.fitlife.app.Model.Trainer.Chat;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChatMapper {
    private final ModelMapper modelMapper;

    public ChatDto chatDto(Chat entity) {
        TypeMap<Chat, ChatDto> typeMap = modelMapper.typeMap(Chat.class, ChatDto.class);
        return typeMap.map(entity);
    }
}
