package com.FitnessApp.Mapper;

import com.FitnessApp.DTO.SessionDTO;
import com.FitnessApp.Model.Session;
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

