package com.fitlife.app.Utils.Mapper;

import com.fitlife.app.DTO.DataClass.ActivitiesLog.ActivitiesLogDTO;
import com.fitlife.app.Model.ActivitiesLog;
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
