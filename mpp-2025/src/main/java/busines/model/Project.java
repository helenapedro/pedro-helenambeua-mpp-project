package busines.model;

import java.util.Objects;

public class Project {
    private int projectId, estimatedDays;
    private String projectName, location;

    public Project() {}

    public Project(int projectId, String projectName, int estimatedDays, String location) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.estimatedDays = estimatedDays;
        this.location = location;
    }

    public int getProjectId() { return projectId; }
    public String getProjectName() { return projectName; }
    public int getEstimatedDays() { return estimatedDays; }
    public String getLocation() { return location; }

    @Override
    public String toString() {
        return "Project{" + projectId + ", " + projectName +
                ", " + estimatedDays + "d, " + location + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project project)) return false;
        return projectId == project.projectId;
    }

    @Override
    public int hashCode() { return Objects.hash(projectId); }
}

