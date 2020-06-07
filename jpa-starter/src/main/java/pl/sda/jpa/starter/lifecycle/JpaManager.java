package pl.sda.jpa.starter.lifecycle;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaManager {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter.lifecycle");
        CourseEntityDao courseEntityDao = new CourseEntityDao(entityManagerFactory);
    }
}
