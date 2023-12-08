package com.fitlife.app.Service.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitlife.app.Model.session.Session;
import com.fitlife.app.Repository.SessionRepository;
import com.fitlife.app.Service.Generic.GenericService;

@Service
public class SessionService extends GenericService<Session, Long, SessionRepository>{

	@Autowired
	SessionRepository sessionRP;

	@Autowired
	public SessionService(SessionRepository workoutPlanRepository) {
		super(workoutPlanRepository);

	}

}
