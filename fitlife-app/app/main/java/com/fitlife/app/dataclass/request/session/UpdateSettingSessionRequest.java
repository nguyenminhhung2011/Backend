package com.fitlife.app.dataclass.request.session;

import lombok.Data;

@Data
public class UpdateSettingSessionRequest {
    private Boolean startWithBoot;
    private Boolean randomMix;
    private String description;
    private String name;
    private String level;
    private int numberRound;
    private int breakTime;

    private int timePerLesson;
    private int transferTime;
    private int calcTarget;
}
