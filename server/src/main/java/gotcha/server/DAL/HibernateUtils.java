package gotcha.server.DAL;

//import com.mysql.cj.Session;
//import net.bytebuddy.asm.Advice;
//
//import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HibernateUtils {
    public static void set_tests_mode() {
    }

    public static void set_normal_use() {
    }

//    private static EntityManagerFactory emf;
//    private static EntityManager em;
//    private static ThreadLocal<EntityManager> threadLocal;
//    private static String persistence_unit = "TradingSystemTests";
//    private static boolean allow_persist = false;
//    private static boolean begin_transaction = true;
//
//    static {
////        emf = Persistence.createEntityManagerFactory(persistence_unit);
//        threadLocal = new ThreadLocal<EntityManager>();
////        em = getEntityManager();
//    }
//
//    public static void setPersistence_unit(String persistence_unit) {
//        HibernateUtils.persistence_unit = persistence_unit;
//        emf = Persistence.createEntityManagerFactory(persistence_unit);
//        threadLocal = new ThreadLocal<EntityManager>();
//    }
//
//
//    public static synchronized EntityManager getEntityManager() {
////        EntityManager em = threadLocal.get();
//
//        if (em == null) {
//            em = emf.createEntityManager();
//            // set your flush mode here
////            threadLocal.set(em);
//        }
//        return em;
//    }
//
//    public static synchronized void closeEntityManager() {
////        EntityManager em = threadLocal.get();
//        if (em != null) {
//            em.close();
//            threadLocal.set(null);
//            em = null;
//        }
//    }
//
//    public static synchronized void clear_db() {
//        if (allow_persist) {
//            closeEntityManager();
//            closeEntityManagerFactory();
//            em = null;
//            emf = Persistence.createEntityManagerFactory(persistence_unit);
////        threadLocal.set(null);
//        }
//    }
//
//    public static void closeEntityManagerFactory() {
//        if (emf != null) {
//            emf.close();
//            emf = null;
//            em = null;
//        }
//    }
//
//    public static synchronized void beginTransaction() {
//        if (allow_persist && begin_transaction)
//            if (!getEntityManager().getTransaction().isActive())
//                getEntityManager().getTransaction().begin();
//
//    }
//
//    public static synchronized void rollback() {
//        if (allow_persist && begin_transaction) {
//            getEntityManager().getTransaction().rollback();
//            em.close();
//            em = emf.createEntityManager();
//        }
//    }
//
//    public static synchronized void commit() {
//        if (allow_persist && begin_transaction)
//            if (getEntityManager().getTransaction().isActive())
//                getEntityManager().getTransaction().commit();
//    }
//
//    public static synchronized <T> void persist(T obj) {
//        if (allow_persist)
//            getEntityManager().persist(obj);
//    }
//
//    public static synchronized <T> void remove(T obj) {
//        if (allow_persist && getEntityManager().getTransaction().isActive())
//            getEntityManager().remove(obj);
//    }
//
//    public static synchronized <T> T merge(T obj) {
//        if (allow_persist && begin_transaction) {
//            if (!getEntityManager().contains(obj))
//                return getEntityManager().merge(obj);
//        }
//        return obj;
//    }
//
//    public static void setBegin_transaction(boolean begin_transaction) {
//        HibernateUtils.begin_transaction = begin_transaction;
//    }
}