package com.FitnessApp.Service.Generic;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IGenericService <T,ID>{
    List<T> findAll()  throws Exception;
    T save(T entity) throws Exception;
    void delete(ID id) throws Exception;
    //T findById(Long id) throws Exception;
    T findById(ID id) throws Exception;

}
