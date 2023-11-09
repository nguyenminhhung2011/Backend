package com.FitnessApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
@NoRepositoryBean
public interface GenericSearchRepository<E, ID> extends JpaRepository<E,ID>, JpaSpecificationExecutor<E> {
}
