package com.fitlife.app.DTO.DataClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyWorkoutDTO {
    private Long id;
    private String name;
    private String description;
    private Long time;
    private List<SessionDTO> sessions;
}
