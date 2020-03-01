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
//            CoachEntity coachEntity2 = new CoachEntity("Jan Kowalski");
//            CoachEntity coachEntity3 = new CoachEntity("Anna Krawczyk");
//            CoachEntity coachEntity4 = new CoachEntity("Roman Polanski");
//            entityManager.remove(coachEntity);
//            entityManager.persist(coachEntity2);
//            entityManager.persist(coachEntity3);
//            CoachEntity coachEntity1 = entityManager.find(CoachEntity.class, 4);
//            entityManager.remove(coachEntity1);
            StudentEntity studentEntity = new StudentEntity("Jan Kowalski", "2005");
            StudentEntity studentEntity2 = new StudentEntity("Maria Krawczy", "2002");
            StudentEntity studentEntity3 = new StudentEntity("Stefan Polanski", "2011");

            entityManager.persist(studentEntity);
            entityManager.persist(studentEntity2);
            entityManager.persist(studentEntity3);



            /**
             * Wyciągamy wszystkie encje zapisane w bazie danych
             */
//            TypedQuery<CoachEntity> query = entityManager.createQuery("from CoachEntity", CoachEntity.class);
//            List<CoachEntity> coaches = query.getResultList();
//            System.out.println("coaches = " + coaches);

            TypedQuery<StudentEntity> query = entityManager.createQuery("from StudentEntity", StudentEntity.class);
            List<StudentEntity> students = query.getResultList();
            System.out.println("coaches = " + students);

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
