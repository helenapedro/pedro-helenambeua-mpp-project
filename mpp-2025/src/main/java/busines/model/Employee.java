package busines.model;

import java.util.Objects;

public class Employee {
    private int empId;
    private String name;
    private double salary;
    private Address address;
    private Department department;

    // Constructors
    public Employee() {}

    public Employee(int empId, String name, double salary, Address address, Department department) {
        this.empId = empId;
        this.name = name;
        this.salary = salary;
        this.address = address;
        this.department = department;
    }

    // equals & hashCode for comparing Employee objects
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return empId == employee.empId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId);
    }

    // Utility: toString()
    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", department=" + (department != null ? department.getName() : "N/A") +
                ", city=" + (address != null ? address.getCity() : "N/A") +
                '}';
    }

    // Getters (Encapsulation)
    public int getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public Address getAddress() {
        return address;
    }

    public Department getDepartment() {
        return department;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}