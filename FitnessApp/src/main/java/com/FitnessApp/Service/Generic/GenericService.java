package com.FitnessApp.Service.Generic;

import com.FitnessApp.Repository.GenericRepository;
import com.FitnessApp.Repository.GenericSearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public class GenericService<T> implements IGenericService<T> {

    protected final JpaRepository<T,Long> genericRepository;

    public GenericService(JpaRepository<T, Long> genericRepository) {
        this.genericRepository = genericRepository;
    }

    @Override
    public List<T> findAll() {
        return genericRepository.findAll();
    }

    @Override
    public T save(T entity) {
        return genericRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        genericRepository.deleteById(id);
    }

    @Override
    public Optional<T> findById(Long id) {
        return genericRepository.findById(id);
    }

}

