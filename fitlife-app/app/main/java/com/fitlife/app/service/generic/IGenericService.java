package com.fitlife.app.service.generic;

import java.util.List;

public interface IGenericService <T,ID>{
    List<T> findAll()  throws Exception;
    T save(T entity) throws Exception;
    void delete(ID id) throws Exception;
    //T findById(Long id) throws Exception;
    T findById(ID id) throws Exception;

}
