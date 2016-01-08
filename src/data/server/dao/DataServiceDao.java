package data.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by bishruti on 1/2/16.
 */

public enum DataServiceDao {
    instance;
    private EntityManagerFactory emf;

    private DataServiceDao() {
        if (emf!=null) {
            emf.close();
        }
        emf = Persistence.createEntityManagerFactory("dataservice-jpa");
    }

    public EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    public void closeConnections(EntityManager em) {
        em.close();
    }

    public EntityTransaction getTransaction(EntityManager em) {
        return em.getTransaction();
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
