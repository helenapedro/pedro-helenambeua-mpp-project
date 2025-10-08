package com.pedro;

import busines.dao.*;
import busines.model.*;

import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            System.out.println("===== EMPLOYEE CRUD TEST =====");

            // 1️⃣ CREATE
            System.out.println("\n--- CREATE EMPLOYEE ---");
            CreateEmployeeDao createDao = new CreateEmployeeDao(31, "Helena Pedro", 300000.00, 1, 1);
            createDao.getDataAccessSystem().write(createDao);
            System.out.println("Employee created successfully.");

            // 2️⃣ READ ALL
            System.out.println("\n--- FIND ALL EMPLOYEES ---");
            EmployeeDao employeeDao = new EmployeeDao();
            List<Employee> allEmployees = employeeDao.getAllEmployees();
            allEmployees.forEach(System.out::println);

            // 3️⃣ READ ONE (findById)
            System.out.println("\n--- FIND EMPLOYEE BY ID (116) ---");
            FindEmployeeByIdDao findDao = new FindEmployeeByIdDao(116);
            Employee found = findDao.getEmployee();
            System.out.println(found != null ? found : "Employee not found!");

            // 4️⃣ UPDATE
            System.out.println("\n--- UPDATE EMPLOYEE (116) ---");
            if (found != null) {
                found.setName("Helena Updated");
                found.setSalary(320000.00);
                // Optionally change department or address references
                found.setAddress(new Address(1, null, null, null));   // only FK id needed
                found.setDepartment(new Department(1, null));         // only FK id needed
                UpdateEmployeeDao updateDao = new UpdateEmployeeDao(
                        found.getEmpId(),
                        found.getName(),
                        found.getSalary(),
                        found.getAddress().getAddressId(),
                        found.getDepartment().getDeptId()
                );
                updateDao.getDataAccessSystem().write(updateDao);
                System.out.println("Employee updated successfully.");
            }

            // 5️⃣ VERIFY UPDATE
            System.out.println("\n--- VERIFY UPDATED EMPLOYEE ---");
            FindEmployeeByIdDao verifyDao = new FindEmployeeByIdDao(116);
            System.out.println(verifyDao.getEmployee());

            // 6️⃣ DELETE
            System.out.println("\n--- DELETE EMPLOYEE (116) ---");
            DeleteEmployeeDao deleteDao = new DeleteEmployeeDao(116);
            deleteDao.getDataAccessSystem().write(deleteDao);
            System.out.println("Employee deleted successfully.");

            // 7️⃣ VERIFY DELETE
            System.out.println("\n--- FIND ALL AFTER DELETE ---");
            List<Employee> afterDelete = employeeDao.getAllEmployees();
            afterDelete.forEach(System.out::println);

            System.out.println("\n===== CRUD TEST COMPLETE =====");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
