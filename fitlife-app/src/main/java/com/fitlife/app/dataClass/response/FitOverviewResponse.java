package com.fitlife.app.dataClass.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FitOverviewResponse {
    private double todoPercent;

    private int calories;
    private List<ChartResponse> chartData;
}
