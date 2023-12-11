package com.fitlife.app.Utils.Mapper;

import com.fitlife.app.DTO.DataClass.SessionDTO;
import com.fitlife.app.Model.session.Session;
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

