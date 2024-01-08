package com.fitlife.app.utils.mapper;

import com.fitlife.app.dataClass.dto.SessionDTO;
import com.fitlife.app.model.session.Session;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SessionMapper {
    private final ModelMapper modelMapper;
    public SessionDTO fromEntity(Session session){
        TypeMap<Session,SessionDTO> typeMap  =  modelMapper.typeMap(Session.class, SessionDTO.class);
        return typeMap.map(session);
    }
}

