package com.fitlife.app.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitlife.app.model.session.Session;


@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {


}
