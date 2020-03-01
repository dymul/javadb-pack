package pl.sda.hibernate.starter;

public class Student {
    private Integer id;
    private String name;

    private Integer courseId;
    private String description;
    private String seat;

    protected Student(){};

    public Student(String name, Integer courseId, String description, String seat) {
        this.name = name;
        this.courseId = courseId;
        this.description = description;
        this.seat = seat;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public String getDescription() {
        return description;
    }

    public String getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courseId=" + courseId +
                ", description='" + description + '\'' +
                ", seat='" + seat + '\'' +
                '}';
    }
}
