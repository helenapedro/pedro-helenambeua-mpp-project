package dbconn;

import java.sql.*;

public class GetConnection {
    public static void main(String[] args) {
        String sql = "select 1";

        try {
            String url = System.getenv("DATABASE_URL");
            String uname = System.getenv("DATABASE_UNAME");
            String pass = System.getenv("DATABASE_PASS");
            Connection con = DriverManager.getConnection(url,uname,pass);
            System.out.println("Connection Success");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            System.out.println(rs.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
