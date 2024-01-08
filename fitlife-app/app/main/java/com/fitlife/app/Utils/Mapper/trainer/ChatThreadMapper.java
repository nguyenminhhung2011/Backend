package com.fitlife.app.Utils.Mapper.trainer;

import lombok.AllArgsConstructor;
import com.fitlife.app.DTO.Request.Trainer.ChatThreadDto;
import com.fitlife.app.Model.Trainer.ChatThread;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChatThreadMapper {
    private ModelMapper modelMapper;

    public ChatThreadDto chatThreadDto(ChatThread entity){
        TypeMap<ChatThread,ChatThreadDto> typeMap = modelMapper.typeMap(ChatThread.class, ChatThreadDto.class);
        return typeMap.map(entity);
    }
}
