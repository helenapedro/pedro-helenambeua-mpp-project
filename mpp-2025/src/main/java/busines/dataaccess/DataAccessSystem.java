package busines.dataaccess;

import java.sql.*;

public class DataAccessSystem implements DataAccess {
    // package-level constructor
    DataAccessSystem() {}

    @Override
    public void read(Dao dao) throws SQLException {
        final String sql = dao.getSql();
        try (Connection con = ConnectManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            dao.bind(ps); // safe binding (even if it’s a no-op)
            try (ResultSet rs = ps.executeQuery()) {
                dao.unpackResultSet(rs);
            }
        }
        // SQLException is propagated to caller; no System.out in infra layer
    }

    @Override
    public void write(Dao dao) throws SQLException {
        final String sql = dao.getSql();
        final int flags = dao.wantsGeneratedKeys()
                ? Statement.RETURN_GENERATED_KEYS
                : Statement.NO_GENERATED_KEYS;

        try (Connection con = ConnectManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, flags)) {

            dao.bind(ps);
            ps.executeUpdate();

            if (dao.wantsGeneratedKeys()) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    dao.handleGeneratedKeys(keys);
                }
            }
        }
    }

    /** Simple connection manager; opens per call (no caching) */
    public static class ConnectManager {
        private static final String url   = System.getenv("DATABASE_URL");
        private static final String uname = System.getenv("DATABASE_UNAME");
        private static final String pass  = System.getenv("DATABASE_PASS");

        public static Connection getConnection() throws SQLException {
            // Modern JDBC drivers auto-register; if needed: Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, uname, pass);
        }
    }
}
