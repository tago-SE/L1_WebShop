package model.repository.entities;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;

public interface EntityInt extends Serializable {

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
     * Used to copy data from one entity to another when its being updated
     * @param toEntity
     */
    void transferTo(EntityInt toEntity);

    /**
     * Factory method, creates a query that verifies that the unique constraints are met.
     * Used for example when inserting is executed.
     * @param em, EntityManger
     * @return Query
     */
    Query createVerifyIsUniqueQuery(EntityManager em);

    Query createFindByIdQuery(EntityManager em);
}
