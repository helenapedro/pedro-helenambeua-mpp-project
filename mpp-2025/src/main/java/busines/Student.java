package busines;

public class Student {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String major;

    public Student(long id, String firstName, String lastName, String email, String major) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.major = major;
    }

    public long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getMajor() { return major; }

    @Override
    public String toString() {
        return String.format("Student{id=%d, %s %s, email=%s, major=%s}",
                id, firstName, lastName, email, major);
    }
}
