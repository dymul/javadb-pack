package pl.sda.jpa.starter.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class CourseEntityDao {
    private static Logger logger = LoggerFactory.getLogger(CourseEntityDao.class);

    private final EntityManagerFactory entityManagerFactory;

    public CourseEntityDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    // try catch !!!
    public void save(CourseEntity courseEntity) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(courseEntity);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void update(CourseEntity courseEntity) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            CourseEntity managed = entityManager.merge(courseEntity);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void delete(CourseEntity courseEntity) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            CourseEntity managed = entityManager.merge(courseEntity);
            entityManager.remove(managed);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public CourseEntity findById(int id) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            CourseEntity courseEntity = entityManager.find(CourseEntity.class, id);

            entityManager.getTransaction().commit();
            return courseEntity;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<CourseEntity> list() {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            List<CourseEntity> courses = entityManager.createQuery("from CourseEntity", CourseEntity.class).getResultList();

            entityManager.getTransaction().commit();
            return courses;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}