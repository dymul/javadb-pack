package pl.sda.jpa.starter.lifecycle;

import pl.sda.commons.Utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaManager {



    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter.lifecycle");

        CourseEntityDao courseEntityDao = new CourseEntityDao(entityManagerFactory);

    //  CourseEntity javaKrk23 = new CourseEntity("JavaKrk23", "Kraków", Utils.parse("2018-05-05"), Utils.parse("2018-12-01"));

    //    courseEntityDao.delete(5);

      //  courseEntityDao.save(javaKrk23);

        //courseEntityDao.update(4);
       // courseEntityDao.findById(4);
       // courseEntityDao.list();

        courseEntityDao.changeSchedule(java.sql.Date.valueOf("2018-01-01"), java.sql.Date.valueOf("2018-04-01"));


        entityManagerFactory.close();

    }
}
