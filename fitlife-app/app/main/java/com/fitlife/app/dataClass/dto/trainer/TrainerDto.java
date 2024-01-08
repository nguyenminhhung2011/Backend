package com.fitlife.app.dataClass.dto.trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TrainerDto {
    public UUID id;
    public String name;
    public String model;
    public String description;
    public String image;
    public String greetingMessage;
    public String bio;
}
