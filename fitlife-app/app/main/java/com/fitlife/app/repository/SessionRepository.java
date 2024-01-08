package com.fitlife.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitlife.app.model.Session.Session;


@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {


}
