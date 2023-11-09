package com.FitnessApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GenericSearchRepository<E, ID> extends GenericRepository<E, ID>, JpaSpecificationExecutor<E> {
}
