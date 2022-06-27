package com.codegym.service;

import com.codegym.model.Student;

import java.util.List;

public interface StudentService extends IGeneralService<Student> {
    List<Student> findById(int id);
}
