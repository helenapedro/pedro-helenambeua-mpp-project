package busines;

import busines.model.Address;
import busines.model.Department;
import busines.model.Employee;
import busines.dataaccess.DataAccessSystem.ConnectManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    // ---------- CRUD lifecycle ----------
    public static void main(String[] args) {
        EmployeeRepository repo = new EmployeeRepository();

        // 1) Create a new employee
        Employee newEmp = new Employee(900, "Helena Repo", 155000.00,
                new Address(1, null, null, null), // only id is required for FK
                new Department(1, null)           // only id is required for FK
        );
        System.out.println("CREATE -> " + repo.create(newEmp));

        // 2) findAll()
        System.out.println("\nFIND ALL (after create):");
        repo.findAll().forEach(System.out::println);

        // 3) findById()
        System.out.println("\nFIND BY ID 900:");
        Employee fetched = repo.findById(900);
        System.out.println(fetched);

        // 4) update
        if (fetched != null) {
            fetched.setName("Helena Repo (Updated)");
            fetched.setSalary(160000.00);
            // optional: change address/department ids
            System.out.println("\nUPDATE -> " + repo.update(fetched));
        }

        // 5) verify update
        System.out.println("\nFIND BY ID 900 (after update):");
        System.out.println(repo.findById(900));

        // 6) delete
        System.out.println("\nDELETE 900 -> " + repo.delete(900));

        // 7) final findAll() to confirm deletion
        System.out.println("\nFIND ALL (after delete):");
        repo.findAll().forEach(System.out::println);
    }

    // ---------- Public API ----------

    public List<Employee> findAll() {
        final String sql = """
            SELECT e.emp_id, e.name, e.salary,
                   a.address_id, a.city, a.state, a.zipcode,
                   d.dept_id, d.name AS dept_name
            FROM employee e
            JOIN address a ON e.address_id = a.address_id
            JOIN department d ON e.dept_id = d.dept_id
            ORDER BY e.emp_id
        """;
        List<Employee> out = new ArrayList<>();
        try (Connection con = ConnectManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(mapEmployee(rs));
            }
        } catch (SQLException ex) {
            System.err.println("findAll error: " + ex.getMessage());
        }
        return out;
    }

    public Employee findById(int empId) {
        final String sql = """
            SELECT e.emp_id, e.name, e.salary,
                   a.address_id, a.city, a.state, a.zipcode,
                   d.dept_id, d.name AS dept_name
            FROM employee e
            JOIN address a ON e.address_id = a.address_id
            JOIN department d ON e.dept_id = d.dept_id
            WHERE e.emp_id = ?
        """;
        try (Connection con = ConnectManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapEmployee(rs);
            }
        } catch (SQLException ex) {
            System.err.println("findById error: " + ex.getMessage());
        }
        return null;
    }

    /** Inserts a new employee. Expects Address(id) and Department(id) to exist. */
    public boolean create(Employee e) {
        final String sql = """
            INSERT INTO employee (emp_id, name, salary, address_id, dept_id)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection con = ConnectManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, e.getEmpId());
            ps.setString(2, e.getName());
            ps.setDouble(3, e.getSalary());
            ps.setInt(4, e.getAddress().getAddressId());
            ps.setInt(5, e.getDepartment().getDeptId());
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.err.println("create error: " + ex.getMessage());
            return false;
        }
    }

    /** Updates name, salary, address_id, dept_id for the given emp_id. */
    public boolean update(Employee e) {
        final String sql = """
            UPDATE employee
               SET name = ?, salary = ?, address_id = ?, dept_id = ?
             WHERE emp_id = ?
        """;
        try (Connection con = ConnectManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getName());
            ps.setDouble(2, e.getSalary());
            ps.setInt(3, e.getAddress().getAddressId());
            ps.setInt(4, e.getDepartment().getDeptId());
            ps.setInt(5, e.getEmpId());
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            System.err.println("update error: " + ex.getMessage());
            return false;
        }
    }

    /**
     * Deletes an employee by id.
     */
    public boolean delete(int empId) {
        final String deleteEmployee = "DELETE FROM employee WHERE emp_id = ?";

        try (Connection con = ConnectManager.getConnection()) {
            con.setAutoCommit(false);
            try (
                    PreparedStatement ps = con.prepareStatement(deleteEmployee)
            ) {
                ps.setInt(1, empId);
                int rows = ps.executeUpdate();

                con.commit();
                return rows == 1;
            } catch (SQLException ex) {
                con.rollback();
                System.err.println("delete error (rolled back): " + ex.getMessage());
                return false;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            System.err.println("delete connection error: " + ex.getMessage());
            return false;
        }
    }

    // ---------- Helper: map row -> domain ----------
    private Employee mapEmployee(ResultSet rs) throws SQLException {
        Address address = new Address(
                rs.getInt("address_id"),
                safeTrim(rs.getString("city")),
                safeTrim(rs.getString("state")),
                safeTrim(rs.getString("zipcode"))
        );
        Department dept = new Department(
                rs.getInt("dept_id"),
                safeTrim(rs.getString("dept_name"))
        );
        return new Employee(
                rs.getInt("emp_id"),
                safeTrim(rs.getString("name")),
                rs.getDouble("salary"),
                address,
                dept
        );
    }

    private String safeTrim(String s) { return s == null ? null : s.trim(); }
}
