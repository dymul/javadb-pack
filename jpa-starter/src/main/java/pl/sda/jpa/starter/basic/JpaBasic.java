package pl.sda.jpa.starter.basic;

import javax.persistence.*;
import java.util.List;

public class JpaBasic {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            /**
             * Tworzymy nowy obiekt EntityManagerFactory z konfiguracją Persistence Unit o nazwie: "pl.sda.jpa.starter"
             */
            entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter");
            /**
             * tworzymy nową instancję EntityManager
             */
            entityManager = entityManagerFactory.createEntityManager();

            /**
             * Do pracy z bazą danych potrzebujemy transakcji
             */
            EntityTransaction transaction = entityManager.getTransaction();
            /**
             * Zaczynamy nową transakcję, każda operacja na bazie danych musi być "otoczona" transakcją
             */
            transaction.begin();

            /**
             * Zapisujemy encję w bazie danych
             */
//            CoachEntity coachEntity = new CoachEntity("Vlad Mihalcea");
//            CoachEntity coachEntity1 = new CoachEntity("Jakub Domagała");
//            CoachEntity coachEntity2 = new CoachEntity("Tomasz Mak");
//            entityManager.persist(coachEntity);
//            entityManager.persist(coachEntity1);
//            entityManager.persist(coachEntity2);
//
//
//            entityManager.remove(coachEntity);


            StudentEntity student1 = new StudentEntity("Jakub", 3);
            StudentEntity student2 = new StudentEntity("Jacek", 5);

            entityManager.persist(student1);
            entityManager.persist(student2);

            /**
             * Wyciągamy wszystkie encje zapisane w bazie danych
             */
//            TypedQuery<CoachEntity> query = entityManager.createQuery("from CoachEntity", CoachEntity.class);
//            List<CoachEntity> coaches = query.getResultList();
//            System.out.println("coaches = " + coaches);

            TypedQuery<StudentEntity> query = entityManager.createQuery("from StudentEntity", StudentEntity.class);
            List<StudentEntity> school = query.getResultList();
            System.out.println("school = " + school);
            /**
             * Kończymy (commitujemy) transakcję - wszystkie dane powinny być zapisane w bazie
             */
            transaction.commit();
        } finally {
            /**
             * Kończymy pracę z entityManager, zamykamy go i tym samym zamykamy Persistence Context z nim związany
             * Czemu EntityManager nie implementuje AutoClosable? https://github.com/javaee/jpa-spec/issues/77
             */

            if (entityManager != null) {
                entityManager.close();
                entityManagerFactory.close();
            }
        }
    }
}
