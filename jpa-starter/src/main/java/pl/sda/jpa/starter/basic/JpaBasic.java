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
//            CoachEntity coachEntity = new CoachEntity("Alan");
//            CoachEntity coachEntity2 = new CoachEntity("Kamil");
//            CoachEntity coachEntity3 = new CoachEntity("Karol");
//            entityManager.persist(coachEntity);
//            entityManager.persist(coachEntity2);
//            entityManager.persist(coachEntity3);


            StudentEntity student1 = new StudentEntity("Natalia", 4);
            StudentEntity student2 = new StudentEntity("Olek", 3);
            StudentEntity student3 = new StudentEntity("Paweł", 5);

            entityManager.persist(student1);
            entityManager.persist(student2);
            entityManager.persist(student3);


            /**
             * Wyciągamy wszystkie encje zapisane w bazie danych
             */

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
