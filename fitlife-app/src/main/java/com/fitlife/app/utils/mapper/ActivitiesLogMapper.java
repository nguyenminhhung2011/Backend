package com.fitlife.app.utils.mapper;

import com.fitlife.app.dataClass.dto.activitiesLog.ActivitiesLogDTO;
import com.fitlife.app.model.ActivitiesLog;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ActivitiesLogMapper {
    private ModelMapper modelMapper;

    public ActivitiesLogDTO activitiesLogDTO (ActivitiesLog entity){
        TypeMap<ActivitiesLog,ActivitiesLogDTO> typeMap = modelMapper.typeMap(ActivitiesLog.class, ActivitiesLogDTO.class);
        return typeMap.map(entity);
    }

}
