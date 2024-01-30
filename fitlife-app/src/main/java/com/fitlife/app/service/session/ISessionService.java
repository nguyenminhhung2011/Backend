package com.fitlife.app.service.session;

import com.fitlife.app.dataClass.dto.SessionDTO;
import com.fitlife.app.dataClass.request.CustomExerciseRequest;
import com.fitlife.app.dataClass.request.session.SessionRequest;
import com.fitlife.app.dataClass.request.session.UpdateSettingSessionRequest;
import com.fitlife.app.dataClass.response.CustomExerciseResponse;
import com.fitlife.app.exceptions.appException.BadRequestException;

import java.util.List;

public interface ISessionService {
    SessionDTO createSession(SessionRequest req) throws BadRequestException;

    List<SessionDTO> getAllSession(String dailyID) throws BadRequestException;

    CustomExerciseResponse createCustomExercise(CustomExerciseRequest req, String sessionId) throws BadRequestException;

    SessionDTO getSessionById(String id);
    SessionDTO completeSession(String id) throws BadRequestException;
    SessionDTO updateSettingSession(UpdateSettingSessionRequest request, Long id) throws BadRequestException;

    List<SessionDTO> getUpComingSession() throws  BadRequestException;

    void delete(Long id );
}
