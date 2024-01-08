package com.fitlife.app.utils.mapper.trainer;

import com.fitlife.app.dataClass.dto.trainer.TrainerDetailDto;
import com.fitlife.app.dataClass.dto.trainer.TrainerDto;
import com.fitlife.app.model.trainer.Trainer;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrainerMapper {

    private final ModelMapper modelMapper;

    public TrainerDto toDto(Trainer entity){
        TypeMap<Trainer,TrainerDto> typeMap = modelMapper.typeMap(Trainer.class, TrainerDto.class);
        return typeMap.map(entity);
    }

    public TrainerDetailDto toDetailDto(Trainer entity){
        TypeMap<Trainer,TrainerDetailDto> typeMap = modelMapper.typeMap(Trainer.class, TrainerDetailDto.class);
        return typeMap.map(entity);
    }
}
