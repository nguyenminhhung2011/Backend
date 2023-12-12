package com.fitlife.app.Service.Session;

import com.fitlife.app.DTO.DataClass.SessionDTO;
import com.fitlife.app.DTO.Request.CustomExerciseRequest;
import com.fitlife.app.DTO.Request.Session.SessionRequest;
import com.fitlife.app.DTO.Request.Session.UpdateSettingSessionRequest;
import com.fitlife.app.DTO.Response.CustomExerciseResponse;
import com.fitlife.app.Exceptions.AppException.BadRequestException;

import java.util.List;

public interface ISessionService {
    SessionDTO createSession(SessionRequest req) throws BadRequestException;

    List<SessionDTO> getAllSession(String dailyID) throws BadRequestException;

    CustomExerciseResponse createCustomExercise(CustomExerciseRequest req, String sessionId) throws BadRequestException;

    SessionDTO getSessionById(String id);
    SessionDTO updateSettingSession(UpdateSettingSessionRequest request, Long id) throws BadRequestException;

    List<SessionDTO> getUpComingSession() throws  BadRequestException;

    void delete(Long id );
}
