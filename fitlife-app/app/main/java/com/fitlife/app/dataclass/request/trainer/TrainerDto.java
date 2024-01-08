package com.fitlife.app.dataclass.request.trainer;
import lombok.Data;
import java.util.UUID;

@Data
public class TrainerDto {
    public UUID id;
    public String name;
    public String model;
}
