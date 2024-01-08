package com.fitlife.app.utils.mapper.trainer;

import com.fitlife.app.dataClass.dto.trainer.ChatThreadDto;
import lombok.AllArgsConstructor;
import com.fitlife.app.dataClass.dto.trainer.ChatThreadDetailDto;
import com.fitlife.app.model.trainer.ChatThread;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ChatThreadMapper {
    private ModelMapper modelMapper;

    public ChatThreadDetailDto toDetailDto(ChatThread entity){
        TypeMap<ChatThread, ChatThreadDetailDto> typeMap = modelMapper.typeMap(ChatThread.class, ChatThreadDetailDto.class);
        return typeMap.map(entity);
    }

    public ChatThreadDto toDto(ChatThread entity){
        TypeMap<ChatThread, ChatThreadDto> typeMap = modelMapper.typeMap(ChatThread.class, ChatThreadDto.class);
        return typeMap.map(entity);
    }

}
