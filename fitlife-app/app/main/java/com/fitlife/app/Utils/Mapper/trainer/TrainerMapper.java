package com.fitlife.app.Utils.Mapper.trainer;

import com.fitlife.app.DTO.Request.Trainer.TrainerDto;
import com.fitlife.app.Model.Trainer.Trainer;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
@AllArgsConstructor
public class TrainerMapper {
    private final ModelMapper modelMapper;

    public TrainerDto trainerDto(Trainer entity){
        TypeMap<Trainer,TrainerDto> typeMap = modelMapper.typeMap(Trainer.class, TrainerDto.class);
        return typeMap.map(entity);
    }
}
