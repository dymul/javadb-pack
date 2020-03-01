package pl.sda.jpa.starter.basic;


import javax.persistence.*;

@Entity

@Table(name="students")
public class StudentEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String fullName;

    @Column
    private int yearOfStudy;

    StudentEntity() {}

    public StudentEntity(String fullName, int yearOfStudy) {
        this.fullName = fullName;
        this.yearOfStudy = yearOfStudy;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", yearOfStudy=" + yearOfStudy +
                '}';
    }
}



