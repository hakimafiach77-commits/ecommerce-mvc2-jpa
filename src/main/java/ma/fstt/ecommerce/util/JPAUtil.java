package ma.fstt.ecommerce.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JPAUtil {
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("ecommercePU");
    private JPAUtil() {}
    public static EntityManager createEntityManager() { return EMF.createEntityManager(); }
    public static void close() { if (EMF.isOpen()) EMF.close(); }
}
