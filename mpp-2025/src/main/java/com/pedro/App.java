package com.pedro;

import busines.Student;
import busines.dao.*;

import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            var dao = new InsertStudentDao("Helena", "Pedro", "hpedro@miu.edu", "CS");
            dao.getDataAccessSystem().write(dao);
            System.out.println("New student id: " + dao.getGeneratedId());

            // To verify the insert
            StudentDao studentDao = new StudentDao();
            List<Student> list = studentDao.getAllStudents();
            list.forEach(System.out::println);

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
