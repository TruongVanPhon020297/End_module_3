package com.codegym.service;

import com.codegym.model.Classroom;
import com.codegym.utils.MySQLConnUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassroomServiceImpl implements ClassroomService{
    private static final String SELECT_ALL_CLASS= "SELECT * FROM classroom;";
    private static final String EXIST_CLASS_ID = "SELECT COUNT(*) AS count FROM classroom AS c WHERE c.id = ?;";
    @Override
    public List<Classroom> findAll() {
        List<Classroom> classroomList = new ArrayList<>();
        try{
            Connection connection = MySQLConnUtils.getConnection();
            PreparedStatement statement = connection.prepareCall(SELECT_ALL_CLASS);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String className = rs.getString("class_name");
                classroomList.add(new Classroom(id,className));
            }
        }catch (SQLException e){
            MySQLConnUtils.printSQLException(e);
        }
        return classroomList;
    }

    @Override
    public boolean create(Classroom classroom) {
        return false;
    }

    @Override
    public boolean update(Classroom classroom) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Classroom> search(String keySearch) {
        return null;
    }

    @Override
    public boolean exists(int id) {
        boolean exists = false;

        try {
            Connection connection = MySQLConnUtils.getConnection();
            PreparedStatement statement = connection.prepareCall(EXIST_CLASS_ID);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int count = rs.getInt("count");

                if (count > 0) {
                    exists = true;
                }
            }

        } catch (SQLException e) {
            MySQLConnUtils.printSQLException(e);
        }
        return exists;
    }
}
