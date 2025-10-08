package busines.dao;

import busines.dataaccess.*;
import java.sql.*;
import java.util.List;

/** Deletes one Employee row by emp_id (and cascades Employee_Project if FK has ON DELETE CASCADE). */
public class DeleteEmployeeDao implements Dao {
    private final int empId;
    private DataAccess da;
    private int rowsAffected = 0;

    public DeleteEmployeeDao(int empId) {
        this.empId = empId;
    }

    public int execute() {
        try {
            if (da == null) da = getDataAccessSystem();
            da.write(this);
        } catch (SQLException e) {
            System.out.println("DeleteEmployeeDao error: " + e.getMessage());
        }
        return rowsAffected;
    }

    @Override public DataAccess getDataAccessSystem() {
        return DataAccessFactory.getDataAccess();
    }

    @Override
    public String getSql() {
        return "DELETE FROM employee WHERE emp_id = ?";
    }

    @Override
    public void bind(PreparedStatement ps) throws SQLException {
        ps.setInt(1, empId);
    }

    @Override public List<?> getResults() {
        return List.of(rowsAffected);
    }

    public void setRowsAffected(int n) {
        this.rowsAffected = n;
    }
}
