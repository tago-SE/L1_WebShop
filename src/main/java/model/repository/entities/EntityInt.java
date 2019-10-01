package model.repository.entities;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;

public interface EntityInt extends Serializable {

    /**
     * Used to maintain consistency during CRUD operations of entities, return true if INSERT is allowed to continue.
     * @return
     */
    boolean beforeInsert(EntityManager em);

    /**
     * Used to maintain consistency during CRUD operations of entities, return true if DELETE is allowed to continue.
     * @return
     */
    boolean beforeDelete(EntityManager em);

    /**
     * Method used to perform an update on an entity
     * @param em
     * @param toEntity
     */
    void update(EntityManager em, EntityInt toEntity);

    /**
     * Returns the entity identifier
     * @return id
     */
    int getId();

    /**
     * Returns the version index of a managed object
     * @return version
     */
    int getVersion();

    /**
     * Factory method, creates a query that verifies that the unique constraints are met.
     * Used for example when inserting is executed.
     * @param em, EntityManger
     * @return Query
     */
    Query createVerifyIsUniqueQuery(EntityManager em);

}
