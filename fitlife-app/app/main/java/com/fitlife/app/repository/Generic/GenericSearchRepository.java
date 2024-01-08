package com.fitlife.app.repository.Generic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericSearchRepository<E, ID> extends JpaRepository<E,ID>, JpaSpecificationExecutor<E> {
}
