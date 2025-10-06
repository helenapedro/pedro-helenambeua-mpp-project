package busines.dao;

import busines.Student;
import busines.dataaccess.*;

import java.sql.*;
import java.util.List;

public class InsertStudentDao implements Dao {
    private final String firstName, lastName, email, major;
    private long generatedId = -1L; // DB auto-increment id

    public InsertStudentDao(String firstName, String lastName, String email, String major) {
        if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("firstName required");
        if (lastName == null  || lastName.isBlank())  throw new IllegalArgumentException("lastName required");
        if (email == null     || email.isBlank())     throw new IllegalArgumentException("email required");
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
        this.major     = major; // may be null/blank, that’s OK
    }


    @Override
    public DataAccess getDataAccessSystem() {
        return DataAccessFactory.getDataAccess();
    }

    @Override
    public String getSql() {
        return """
            INSERT INTO students (first_name, last_name, email, major)
            VALUES (?, ?, ?, ?)
            """;
    }

    @Override
    public void bind(PreparedStatement ps) throws SQLException {
        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, email);
        if (major == null || major.isBlank()) {
            ps.setNull(4, Types.VARCHAR);
        } else {
            ps.setString(4, major);
        }
    }

    @Override
    public boolean wantsGeneratedKeys() {
        return true;
    }

    @Override
    public void handleGeneratedKeys(ResultSet keys) throws SQLException {
        if (keys.next())
            generatedId = keys.getLong(1);
    }

    // Not used for INSERT; return empty list
    @Override
    public List<?> getResults() {
        return List.of();
    }

    public long getGeneratedId() { return generatedId; }

    public Student toStudent() {
        return new Student(generatedId, firstName, lastName, email, major);
    }
}
