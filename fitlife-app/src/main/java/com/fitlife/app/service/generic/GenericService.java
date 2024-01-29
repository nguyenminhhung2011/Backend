package com.fitlife.app.service.generic;

import com.fitlife.app.exceptions.appException.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class GenericService<T,ID,R extends JpaRepository<T,ID>> implements IGenericService<T,ID> {

    protected final R repository;

    public GenericService(R repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Override
    public T findById(ID id) {
        Optional<T> entity = repository.findById(id);
        if (entity.isEmpty()){
            throw new NotFoundException("Can not found");
        }
        return entity.get();
    }

}

