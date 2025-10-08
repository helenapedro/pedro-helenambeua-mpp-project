package busines.dao;

import busines.dataaccess.*;
import busines.model.*;

import java.sql.*;
import java.util.*;

public class EmployeeDao implements Dao {
    private DataAccess da;
    private List<Employee> allEmployees;

    public EmployeeDao() {}

    /** Public API: load all employees */
    public List<Employee> getAllEmployees() {
        try {
            if (da == null) da = getDataAccessSystem();
            da.read(this); // executes getSql() + bind() + unpackResultSet()
            return allEmployees == null ? List.of() : allEmployees;
        } catch (SQLException e) {
            // You may rethrow or log via your logger; printing is kept here for brevity
            System.out.println("EmployeeDao.getAllEmployees error: " + e.getMessage());
            return List.of();
        }
    }

    // ---------- Dao contract ----------

    @Override
    public DataAccess getDataAccessSystem() {
        return DataAccessFactory.getDataAccess();
    }

    @Override
    public String getSql() {
        return """
        SELECT e.emp_id, e.name, e.salary,
               a.address_id, a.city, a.state, a.zipcode,
               d.dept_id, d.name AS dept_name
        FROM employee e
        JOIN address a   ON e.address_id = a.address_id
        JOIN department d ON e.dept_id = d.dept_id
        ORDER BY e.emp_id
    """;
    }

    @Override
    public void unpackResultSet(ResultSet rs) throws SQLException {
        List<Employee> out = new ArrayList<>();
        while (rs.next()) {
            int empId = rs.getInt("emp_id");
            String name = rs.getString("name");
            double salary = rs.getDouble("salary");

            Address address = new Address(
                    rs.getInt("address_id"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("zipcode")
            );

            Department dept = new Department(
                    rs.getInt("dept_id"),
                    rs.getString("dept_name")
            );

            if (name != null) name = name.trim();
            out.add(new Employee(empId, name, salary, address, dept));
        }
        this.allEmployees = out;
    }

    @Override
    public boolean wantsGeneratedKeys() {
        return false;
    }

    @Override
    public void handleGeneratedKeys(ResultSet keys) throws SQLException {
        // no-op
    }

    @Override
    public List<?> getResults() {
        return allEmployees == null ? List.of() : allEmployees;
    }
}
