package pl.sda.hibernate.starter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.commons.Utils;

public class HibernateConfiguration {
    private static Logger logger = LoggerFactory.getLogger(HibernateConfiguration.class);

    public static void main(String[] args) {
        /**
         * Krok 1: prosta konfiguracja Hibernate: tworzymy obiekt klasy Configuration i
         * podajemy mu plik z konfiguracją: "hibernate.cfg.xml" - plik znajduje się w katalogu resources
        */
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        /**
         * Krok 2: tworzymy dwa obiekty: SessionFactory i Session z konfiguracji, którą wcześniej przygotowaliśmy
         */
        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession()) {

            /**
             * Krok 3: zaczynamy nową transakcję, każda operacja na bazie danych musi być "otoczona" transakcją
             */
            Transaction transaction = session.beginTransaction();

            Course course = new Course("JavaGda11", "Sopot", Utils.parse("2018-01-01"), Utils.parse("2018-09-01"));
            logger.info("Before: {}", course);
            Integer id = (Integer) session.save(course);
            logger.info("Id: {}", id);
            logger.info("After: {}", course);

            course = new Course("JavaGda15", "Gdansk", Utils.parse("2018-05-11"), Utils.parse("2018-12-11"));
            logger.info("Before: {}", course);
            id = (Integer) session.save(course);
            logger.info("Id: {}", id);
            logger.info("After: {}", course);

            Student student = new Student("Kamil", 3, "abc", "def");
            id = (Integer) session.save(student);


            /**
             * Krok 4: kończymy transakcję - wszystkie dane powinny być zapisane w bazie
             */
            transaction.commit();

            /**
             * Krok 5: niejawnie zamykamy: sessionFactory i session
             */
        }
    }
}