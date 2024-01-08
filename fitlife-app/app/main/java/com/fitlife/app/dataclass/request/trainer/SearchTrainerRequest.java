package com.fitlife.app.DTO.Request.Trainer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchTrainerRequest {
    String name;
    String id;
}
