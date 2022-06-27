package com.codegym.service;

import com.codegym.model.Student;
import com.codegym.utils.MySQLConnUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.codegym.utils.MySQLConnUtils.printSQLException;

public class StudentServiceImpl implements StudentService{
    private static final String SELECT_ALL_STUDENT = "SELECT * FROM student;";
    private static final String CREATE_STUDENT = "INSERT INTO student (name, dob, address, phone_number, email, id_class) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String DELETE_STUDENT= "DELETE FROM student WHERE id = ? ;";
    private static final String FIND_STUDENT_ID = "SELECT * FROM student AS s WHERE s.id = ?;";
    private  static final String UPDATE_STUDENT = "UPDATE student SET name = ?, dob = ?, address = ?, phone_number = ?, email = ?, id_class = ? WHERE id = ?;";
    private static final String EXIST_STUDENT_ID = "SELECT COUNT(*) AS count FROM student AS s WHERE s.id = ?;";
    private static final String SEARCH_STUDENT = "SELECT * FROM student AS s WHERE s.name LIKE ?;";
    @Override
    public List<Student> findAll() {
        List<Student> studentList = new ArrayList<>();
        try{
            Connection connection = MySQLConnUtils.getConnection();
            PreparedStatement statement = connection.prepareCall(SELECT_ALL_STUDENT);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                LocalDate dob = LocalDate.parse(rs.getString("dob"));
                String phone = rs.getString("phone_number");
                String email = rs.getString("email");
                String address = rs.getString("address");
                int classId = rs.getInt("id_class");
                studentList.add(new Student(id,name,dob,address,phone,email,classId));
            }
        }catch (SQLException e){
            printSQLException(e);
        }
        return studentList;
    }

    @Override
    public boolean create(Student student) {
        boolean check = false;
        try {
            Connection connection = MySQLConnUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_STUDENT);
            preparedStatement.setString(1,student.getName());
            preparedStatement.setString(2,String.valueOf(student.getDob()));
            preparedStatement.setString(3,student.getAddress());
            preparedStatement.setString(4,student.getPhoneNumber());
            preparedStatement.setString(5,student.getEmail());
            preparedStatement.setInt(6,student.getIdClass());
            preparedStatement.executeUpdate();
            check = true;
        }catch (SQLException e){
            printSQLException(e);
        }
        return check;
    }

    @Override
    public boolean update(Student student) {
        boolean check = false;
        try{
            Connection connection = MySQLConnUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT);
            preparedStatement.setString(1,student.getName());
            preparedStatement.setString(2,String.valueOf(student.getDob()));
            preparedStatement.setString(3,student.getAddress());
            preparedStatement.setString(4,student.getPhoneNumber());
            preparedStatement.setString(5,student.getEmail());
            preparedStatement.setInt(6,student.getIdClass());
            preparedStatement.setInt(7,student.getId());
            preparedStatement.executeUpdate();
            System.out.println(preparedStatement);
            check = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    @Override
    public boolean delete(int id) {
        boolean check = false;
        try{
            Connection connection = MySQLConnUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            System.out.println(preparedStatement);
            check = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    @Override
    public List<Student> search(String keySearch) {
        List<Student> studentList = new ArrayList<>();
        try{
            Connection connection = MySQLConnUtils.getConnection();
            PreparedStatement statement = connection.prepareCall(SEARCH_STUDENT);
            statement.setString(1,'%' + keySearch + '%');
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                LocalDate dob = LocalDate.parse(rs.getString("dob"));
                String phone = rs.getString("phone_number");
                String email = rs.getString("email");
                String address = rs.getString("address");
                int classId = rs.getInt("id_class");
                studentList.add(new Student(id,name,dob,address,phone,email,classId));
            }
        }catch (SQLException e){
            printSQLException(e);
        }
        return studentList;
    }

    @Override
    public boolean exists(int id) {
        boolean exists = false;

        try {
            Connection connection = MySQLConnUtils.getConnection();
            PreparedStatement statement = connection.prepareCall(EXIST_STUDENT_ID);
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

    @Override
    public List<Student> findById(int id) {
        List<Student> studentList = new ArrayList<>();
        try(
                Connection connection = MySQLConnUtils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_STUDENT_ID);
        ){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                int idFind = rs.getInt("id");
                String name = rs.getString("name");
                LocalDate dob = LocalDate.parse(rs.getString("dob"));
                String address = rs.getString("address");
                String phone = rs.getString("phone_number");
                String email = rs.getString("email");
                int classId = rs.getInt("id_class");
                studentList.add(new Student(idFind,name,dob,address,phone,email,classId));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }
}
