package model.repository.DAO;

import model.repository.entities.EntityInt;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class AbstractDB {

    private static final String PERSISTENCE_UNIT_NAME = "org.hibernate.lab1_web_shop.jpa";

    public static EntityManagerFactory getEntityManagerFactory() {
        return  Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

}
