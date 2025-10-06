package com.pedro;

import busines.dataaccess.DataAccessSystem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionSmokeTest {
    @Test
    void canConnectAndPing() throws Exception {
        try (Connection con = DataAccessSystem.ConnectManager.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT 1");
             ResultSet rs = ps.executeQuery()) {
            assertTrue(rs.next(), "SELECT 1 should return one row");
        }
    }
}