package com.qulix.yurkevichvv.trainingtask.model.services;

import com.qulix.yurkevichvv.trainingtask.model.entity.Entity;

import java.sql.SQLException;
import java.util.List;

public interface IService<T extends Entity> {

    void add(T entity);

    void update(T entity) throws SQLException;

    void delete(Integer id);

    List<T> findAll();

    T getById(Integer id);
}
