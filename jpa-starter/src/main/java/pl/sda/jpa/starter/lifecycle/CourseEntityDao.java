package pl.sda.jpa.starter.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseEntityDao {
    private static Logger logger = LoggerFactory.getLogger(CourseEntityDao.class);

    EntityManagerFactory entityManagerFactory;

    public CourseEntityDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<CourseEntity> changeSchedule(java.sql.Date start, java.sql.Date end){
        EntityManager entityManager = null;
        List<CourseEntity> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            int id = 1;
            while (entityManager.find(CourseEntity.class, id)!=null) {
                CourseEntity entityFromDB = entityManager.find(CourseEntity.class, id);

                if (entityFromDB.getStartDate().after(start) && entityFromDB.getStartDate().before(end)) {
//
                    list.add(entityFromDB);
                }
                id++;
            }

            for (CourseEntity a: list) {
                System.out.println(a);
            }

            transaction.commit();
            return list;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }


    }

    public void save(CourseEntity courseEntity) {
        EntityManager entityManager = null;
        try {
            /**
             * Tworzymy nową instancję EntityManager, tym samym rozpoczynamy działanie Persistence Context
             * Początek sesji logicznje
             */
            entityManager = entityManagerFactory.createEntityManager();
            /**
             * Początek transakcji nr 1 bazodanowej
             */
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            logger.info("Before: {}", courseEntity);
            logger.info("Contains: {}", entityManager.contains(courseEntity));
            /**
             * Zapisujemy nowy obiekt w Persistence Context, encja w tej chwili staje się zarządzana przez EntityManager
             * Nie zawsze oznacza to natychmiastowy zapis w bazie danych!
             */
            entityManager.persist(courseEntity);
            /**
             * Czy teraz obiekt session zarządza encją javaGda11?
             */
            logger.info("Contains: {}", entityManager.contains(courseEntity));
            logger.info("After: {}", courseEntity);

            /**
             * commitujemy transakcję nr 1, wszystkie zmiany dotąd niezapisane w bazie muszą być zapisane
             */
            transaction.commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void update(int id) {
        EntityManager entityManager = null;
        Scanner sc = new Scanner(System.in);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            CourseEntity entityFromDB = entityManager.find(CourseEntity.class, id);
            transaction.begin();

            System.out.println("podaj nową nazwę: ");
            String newName = sc.next();
            entityFromDB.setName(newName);

            System.out.println("podaj nową datę zakończenia");
            String newDate = sc.next();
            entityFromDB.setEndDate(Date.valueOf(newDate));

            transaction.commit();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void delete(Integer id) {

        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            CourseEntity entityFromDB = entityManager.find(CourseEntity.class, id);
            transaction.begin();

            entityManager.remove(entityFromDB);
            transaction.commit();
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
            EntityTransaction transaction = entityManager.getTransaction();
            CourseEntity entityFromDB = entityManager.find(CourseEntity.class, id);
            transaction.begin();

            System.out.println(entityFromDB);
            transaction.commit();
            return entityFromDB;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public List<CourseEntity> list() {
        EntityManager entityManager = null;
        List<CourseEntity> list = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            int id = 1;
            while (entityManager.find(CourseEntity.class, id)!=null){
                CourseEntity entityFromDB = entityManager.find(CourseEntity.class,id);
                list.add(entityFromDB);
                id++;
            }

            for (CourseEntity a: list) {
                System.out.println(a);
            }

            transaction.commit();
            return list;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }



}