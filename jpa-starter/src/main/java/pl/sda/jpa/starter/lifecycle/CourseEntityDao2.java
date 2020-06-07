package pl.sda.jpa.starter.lifecycle;

import javax.persistence.EntityManagerFactory;

public class CourseEntityDao2 extends AbstractEntityDao<CourseEntity> {
    public CourseEntityDao2(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory, CourseEntity.class);
    }
}
