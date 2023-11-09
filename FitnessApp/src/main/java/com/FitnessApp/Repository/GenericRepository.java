package com.FitnessApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GenericRepository<E,ID> extends JpaRepository<E,ID>{
}

