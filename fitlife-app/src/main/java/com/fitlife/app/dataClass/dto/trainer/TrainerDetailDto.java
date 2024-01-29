package com.fitlife.app.dataClass.dto.trainer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerDetailDto {
    public String id;
    public String name;
    public String model;
    public String prompt;
    public String image;
    public String greetingMessage;
    public String bio;
    public List<ChatThreadDto> threads;
}
