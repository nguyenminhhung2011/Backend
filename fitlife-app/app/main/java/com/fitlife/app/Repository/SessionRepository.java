package com.fitlife.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitlife.app.Model.Session.Session;


@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {


}
