package busines.model;

import java.util.Objects;

public class Department {
    private int deptId;
    private String name;

    public Department() {}

    public Department(int deptId, String name) {
        this.deptId = deptId;
        this.name = name;
    }

    @Override
    public String toString() { return "Department{" + deptId + ", " + name + "}"; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department that)) return false;
        return deptId == that.deptId;
    }

    @Override
    public int hashCode() { return Objects.hash(deptId); }

    public int getDeptId() { return deptId; }
    public void setDeptId(int deptId) { this.deptId = deptId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
