package busines.model;

import java.util.Objects;

public class EmployeeProject {
    private int empId;
    private int projectId;

    public EmployeeProject() {}

    public EmployeeProject(int empId, int projectId) {
        this.empId = empId;
        this.projectId = projectId;
    }

    public int getEmpId() { return empId; }
    public int getProjectId() { return projectId; }

    @Override
    public String toString() {
        return "EmployeeProject{" + empId + "->" + projectId + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeProject that)) return false;
        return empId == that.empId && projectId == that.projectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, projectId);
    }
}
