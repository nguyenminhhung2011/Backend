package com.fitlife.app.utils.mapper.trainer;

import com.fitlife.app.dataClass.request.trainer.ChatDto;
import com.fitlife.app.model.trainer.Chat;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChatMapper {
    private final ModelMapper modelMapper;

    public ChatDto toDto(Chat entity) {
        TypeMap<Chat, ChatDto> typeMap = modelMapper.typeMap(Chat.class, ChatDto.class);
        return typeMap.map(entity);
    }

}
