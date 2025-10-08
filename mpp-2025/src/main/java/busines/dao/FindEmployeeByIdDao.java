package busines.dao;

import busines.dataaccess.*;
import busines.model.*;

import java.sql.*;
import java.util.List;

public class FindEmployeeByIdDao implements Dao {
    private final int empId;
    private DataAccess da;
    private Employee employee;

    public FindEmployeeByIdDao(int empId) {
        this.empId = empId;
    }

    /** Public API */
    public Employee getEmployee() {
        try {
            if (da == null) da = getDataAccessSystem();
            da.read(this);
        } catch (SQLException e) {
            System.out.println("FindEmployeeByIdDao error: " + e.getMessage());
        }
        return employee;
    }

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
            JOIN address a ON e.address_id = a.address_id
            JOIN department d ON e.dept_id = d.dept_id
            WHERE e.emp_id = ?
        """;
    }

    @Override
    public void bind(PreparedStatement ps) throws SQLException {
        ps.setInt(1, empId);
    }

    @Override
    public void unpackResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
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

            employee = new Employee(
                    rs.getInt("emp_id"),
                    rs.getString("name"),
                    rs.getDouble("salary"),
                    address,
                    dept
            );
        }
    }

    @Override
    public List<?> getResults() {
        return employee == null ? List.of() : List.of(employee);
    }
}
