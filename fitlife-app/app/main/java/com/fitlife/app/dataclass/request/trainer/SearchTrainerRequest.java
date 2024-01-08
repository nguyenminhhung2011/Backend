package com.fitlife.app.dataclass.request.trainer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchTrainerRequest {
    String name;
    String id;
}
