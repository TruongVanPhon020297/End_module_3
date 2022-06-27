package com.codegym.service;

import java.util.List;

public interface IGeneralService<T>{
    List<T> findAll();
    boolean create(T t);
    boolean update(T t);
    boolean delete(int id);
    List<T> search(String keySearch);
    boolean exists(int id);
}
