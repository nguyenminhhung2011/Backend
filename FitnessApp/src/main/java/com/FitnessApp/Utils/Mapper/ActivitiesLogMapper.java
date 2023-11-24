package com.FitnessApp.Utils.Mapper;

import com.FitnessApp.DTO.DataClass.ActivitiesLog.ActivitiesLogDTO;
import com.FitnessApp.Model.ActivitiesLog;
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
