package com.fitlife.app.utils.mapper.trainer;

import com.fitlife.app.dataclass.request.trainer.TrainerDto;
import com.fitlife.app.model.Trainer.Trainer;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrainerMapper {
    private final ModelMapper modelMapper;

    public TrainerDto trainerDto(Trainer entity){
        TypeMap<Trainer,TrainerDto> typeMap = modelMapper.typeMap(Trainer.class, TrainerDto.class);
        return typeMap.map(entity);
    }
}
