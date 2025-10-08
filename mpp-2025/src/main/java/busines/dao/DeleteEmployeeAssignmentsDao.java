package busines.dao;

import busines.dataaccess.*;
import java.sql.*;
import java.util.List;

// 1) Delete assignments (child rows)
public class DeleteEmployeeAssignmentsDao implements Dao {
    private final int empId;
    private DataAccess da;
    private int rowsAffected = 0;

    public DeleteEmployeeAssignmentsDao(int empId) { this.empId = empId; }
    public int execute() {
        try {
            if (da == null) da = getDataAccessSystem();
            da.write(this);
        } catch (SQLException e) {
            System.out.println("DeleteEmployeeAssignmentsDao error: " + e.getMessage());
        }
        return rowsAffected;
    }

    @Override public DataAccess getDataAccessSystem() { return DataAccessFactory.getDataAccess(); }
    @Override public String getSql() { return "DELETE FROM employee_project WHERE emp_id = ?"; }
    @Override public void bind(PreparedStatement ps) throws SQLException { ps.setInt(1, empId); }
    @Override public void unpackResultSet(ResultSet rs) throws SQLException { }
    @Override public boolean wantsGeneratedKeys() { return false; }
    @Override public void handleGeneratedKeys(ResultSet keys) throws SQLException { }
    @Override public List<?> getResults() { return List.of(rowsAffected); }
    public void setRowsAffected(int n) { this.rowsAffected = n; }
}


