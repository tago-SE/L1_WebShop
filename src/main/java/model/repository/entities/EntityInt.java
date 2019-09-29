package model.repository.entities;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;

public interface EntityInt extends Serializable {

    /**
     * Factory method, creates a query that verifies that the unique constraints are met.
     * Used for example when inserting is executed.
     * @param em, EntityManger
     * @return Query
     */
    Query createVerifyIsUniqueQuery(EntityManager em);
}
