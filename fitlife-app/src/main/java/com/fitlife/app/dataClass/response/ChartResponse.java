package com.fitlife.app.dataClass.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartResponse {
    private int calories;
    private Long time;
}
