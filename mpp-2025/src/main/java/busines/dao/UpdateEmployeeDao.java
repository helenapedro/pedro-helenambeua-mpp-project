package busines.dao;

import busines.dataaccess.*;
import java.sql.*;
import java.util.List;

/** Updates one Employee row by emp_id. */
public class UpdateEmployeeDao implements Dao {
    private final int empId;
    private final String name;
    private final double salary;
    private final int addressId;
    private final int deptId;

    private DataAccess da;
    private int rowsAffected = 0;

    public UpdateEmployeeDao(int empId, String name, double salary,
                             int addressId, int deptId) {
        this.empId = empId;
        this.name = name;
        this.salary = salary;
        this.addressId = addressId;
        this.deptId = deptId;
    }

    /** Public API */
    public int execute() {
        try {
            if (da == null) da = getDataAccessSystem();
            da.write(this);
        } catch (SQLException e) {
            System.out.println("UpdateEmployeeDao error: " + e.getMessage());
        }
        return rowsAffected;
    }

    @Override public DataAccess getDataAccessSystem() {
        return DataAccessFactory.getDataAccess();
    }

    @Override
    public String getSql() {
        return """
               UPDATE employee
               SET name = ?, salary = ?, address_id = ?, dept_id = ?
               WHERE emp_id = ?
               """;
    }

    @Override
    public void bind(PreparedStatement ps) throws SQLException {
        ps.setString(1, name);
        ps.setDouble(2, salary);
        ps.setInt(3, addressId);
        ps.setInt(4, deptId);
        ps.setInt(5, empId);
    }

    @Override public List<?> getResults() {
        return List.of(rowsAffected);
    }

    public void setRowsAffected(int n) {
        this.rowsAffected = n;
    }
}

