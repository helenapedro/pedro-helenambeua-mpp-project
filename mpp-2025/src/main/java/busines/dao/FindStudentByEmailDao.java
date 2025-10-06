package busines.dao;

import busines.Student;
import busines.dataaccess.*;
import java.sql.*;
import java.util.*;

public class FindStudentByEmailDao implements Dao {
    private final String email;
    private final List<Student> students = new ArrayList<>();

    public FindStudentByEmailDao(String email) { this.email = email; }

    @Override public DataAccess getDataAccessSystem() {
        return DataAccessFactory.getDataAccess();
    }

    @Override
    public String getSql() {
        return """
            SELECT id, first_name, last_name, email, major
            FROM students
            WHERE email = ?
            """;
    }

    @Override
    public void bind(PreparedStatement ps) throws SQLException {
        ps.setString(1, email);
    }

    @Override
    public void unpackResultSet(ResultSet rs) throws SQLException {
        while (rs.next()) {
            students.add(new Student(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("major")
            ));
        }
    }

    @Override
    public List<?> getResults() { return students; }
}
