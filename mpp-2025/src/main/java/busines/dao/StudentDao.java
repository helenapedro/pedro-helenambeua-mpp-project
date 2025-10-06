package busines.dao;

import busines.Student;
import busines.dataaccess.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao implements Dao {
    private DataAccess da;
    private List<Student> allStudents;

    public StudentDao() {}

    /** Public API: load all students */
    public List<Student> getAllStudents() {
        try {
            if (da == null) da = getDataAccessSystem();
            da.read(this);                        // executes getSql() + bind() + unpackResultSet()
            allStudents = (List<Student>) getResults();
        } catch (SQLException e) {
            System.out.println("StudentDao.getAllStudents error: " + e.getMessage());
        }
        return allStudents;
    }

    // ===== Dao interface =====
    @Override
    public DataAccess getDataAccessSystem() {
        return DataAccessFactory.getDataAccess();
    }

    @Override
    public String getSql() {
        return "SELECT id, first_name, last_name, email, major FROM students";
    }

    @Override
    public void bind(PreparedStatement ps) throws SQLException {
        // No parameters for this query
    }

    @Override
    public void unpackResultSet(ResultSet rs) throws SQLException {
        List<Student> out = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            String first = rs.getString("first_name");
            String last  = rs.getString("last_name");
            String email = rs.getString("email");
            String major = rs.getString("major");

            // defensive trim (handle possible nulls)
            first = first == null ? null : first.trim();
            last  = last  == null ? null : last.trim();
            email = email == null ? null : email.trim();
            major = major == null ? null : major.trim();

            out.add(new Student(id, first, last, email, major));
        }
        this.allStudents = out;
    }

    @Override
    public boolean wantsGeneratedKeys() {
        return false; // SELECT doesn't generate keys
    }

    @Override
    public void handleGeneratedKeys(ResultSet keys) throws SQLException {
        // no-op
    }

    @Override
    public List<?> getResults() {
        return allStudents == null ? List.of() : allStudents;
    }
}
