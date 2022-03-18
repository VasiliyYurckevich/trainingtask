package com.qulix.yurkevichvv.trainingtask.DAO;

import java.sql.SQLException;
import java.util.List;

public interface DAOInterface<T> {
    boolean add(T t) throws SQLException;

    boolean update(T t) throws SQLException;

    boolean delete(Integer t) throws SQLException;

    List<T> getAll() throws SQLException;

    public T getById(Integer id) throws SQLException;


}
