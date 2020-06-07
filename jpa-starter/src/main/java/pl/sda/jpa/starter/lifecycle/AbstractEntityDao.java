package pl.sda.jpa.starter.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class AbstractEntityDao<T> {

    private static Logger logger = LoggerFactory.getLogger(AbstractEntityDao.class);

    private final EntityManagerFactory entityManagerFactory;

    private final Class<T> clazz;

    public AbstractEntityDao(EntityManagerFactory entityManagerFactory, Class<T> clazz) {
        this.entityManagerFactory = entityManagerFactory;
        this.clazz = clazz;
    }
    // try catch !!!
    public void save(T entity) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            entityManager.persist(entity);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void update(T entity) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            T managed = entityManager.merge(entity);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void delete(T entity) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            T managed = entityManager.merge(entity);
            entityManager.remove(managed);

            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public T findById(Object id) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            T entity = entityManager.find(clazz, id);

            entityManager.getTransaction().commit();
            return entity;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<T> list() {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            List<T> courses = entityManager.createQuery("from " + clazz.getSimpleName(), clazz).getResultList();

            entityManager.getTransaction().commit();
            return courses;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}