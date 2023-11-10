package com.FitnessApp.Service.Generic;

import com.FitnessApp.DTO.Request.FetchExerciseRequest;
import com.FitnessApp.DTO.Request.PageRequest;
import com.FitnessApp.Exceptions.AppException.NotFoundException;
import com.FitnessApp.Model.Exercise;
import com.FitnessApp.Repository.GenericSearchRepository;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
public abstract class GenericSearchService<T> implements IGenericService<T> {

    private final PageRequest defaultPageRequest = new PageRequest(1,10);
    protected final GenericSearchRepository<T, Long> genericRepository;

    public GenericSearchService(GenericSearchRepository<T, Long> genericRepository) {
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
    public T findById(Long id) throws NotFoundException{
        Optional<T> entity = genericRepository.findById(id);
        if (entity.isEmpty()){
            throw new NotFoundException("Can not found item");
        }
        return entity.get();
    }

    protected <R> Specification<T> specification(String field,R value){
        return (root, cq, cb) -> cb.like(root.get("name"), "%" + value + "%");
    }

    protected Page<T> searchAllOf(List<Specification<T>> request,PageRequest pageRequest) {
        Specification<T> specCombine = Specification.allOf(request);

        return genericRepository.findAll(specCombine,
                Pageable
                .ofSize(pageRequest.perPage())
                .withPage(pageRequest.page()));
    }

    protected Page<T> searchAllOf(List<Specification<T>> request) {
        Specification<T> specCombine = Specification.allOf(request);
        return genericRepository.findAll(specCombine,Pageable.unpaged());
    }

    protected Page<T> searchAnyOf(List<Specification<T>> request,PageRequest pageRequest) {
        Specification<T> specCombine = Specification.anyOf(request);

        return genericRepository.findAll(specCombine,
                Pageable
                        .ofSize(pageRequest.perPage())
                        .withPage(pageRequest.page()));
    }

    protected Page<T> searchAnyOf(List<Specification<T>> request) {
        Specification<T> specCombine = Specification.anyOf(request);
        return genericRepository.findAll(specCombine,Pageable.unpaged());
    }

    protected Page<T> search(Specification<T> request,PageRequest pageRequest) {
        return genericRepository.findAll(request,Pageable
                .ofSize(pageRequest.perPage())
                .withPage(pageRequest.page()));
    }

    protected Page<T> search(Specification<T> request) {
        return genericRepository.findAll(request,Pageable.unpaged());
    }

    protected Specification<T> anyOf(List<Specification<T>> specifications){
        return Specification.anyOf(specifications);
    }

    protected Specification<T> allOf(List<Specification<T>> specifications){
        return Specification.allOf(specifications);
    }
}
