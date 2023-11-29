package com.FitnessApp.Service.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FitnessApp.Model.Session;
import com.FitnessApp.Repository.SessionRepository;
import com.FitnessApp.Service.Generic.GenericService;

@Service
public class SessionServiceImpl extends GenericService<Session, Long, SessionRepository> {

	@Autowired
	SessionRepository sessionRP;

	@Autowired
	public SessionServiceImpl(SessionRepository workoutPlanRepository) {
		super(workoutPlanRepository);

	}

}
