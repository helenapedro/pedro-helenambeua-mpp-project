package busines.dao;

import busines.dataaccess.*;
import busines.model.*;

import java.sql.*;
import java.util.List;

/** Inserts one Employee row (expects valid address_id and dept_id to exist). */
public class CreateEmployeeDao implements Dao {
    private final int empId;
    private final String name;
    private final double salary;
    private final int addressId;
    private final int deptId;

    private DataAccess da;
    private int rowsAffected = 0;

    public CreateEmployeeDao(int empId, String name, double salary, int addressId, int deptId) {
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
            System.out.println("CreateEmployeeDao error: " + e.getMessage());
        }
        return rowsAffected;
    }

    // ----- Dao contract -----
    @Override public DataAccess getDataAccessSystem() { return DataAccessFactory.getDataAccess(); }

    @Override
    public String getSql() {
        return "INSERT INTO employee (emp_id, name, salary, address_id, dept_id) " +
                "VALUES (?,?,?,?,?)";
    }

    @Override
    public void bind(PreparedStatement ps) throws SQLException {
        ps.setInt(1, empId);
        ps.setString(2, name);
        ps.setDouble(3, salary);
        ps.setInt(4, addressId);
        ps.setInt(5, deptId);
    }

    @Override public void unpackResultSet(ResultSet rs) throws SQLException { /* no SELECT */ }

    @Override public List<?> getResults() {
        return List.of(rowsAffected);
    }

    public void setRowsAffected(int n) { this.rowsAffected = n; }
}
