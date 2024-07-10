package io.github.Lab3.db;

import io.github.Lab3.model.CheckAreaBean;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtils {
    private static final SessionFactory factory;

    static {
        try {
            factory = new Configuration().configure()
                    .setProperty(AvailableSettings.USER,
                            "s373751")
                    .setProperty(AvailableSettings.PASS,
                            "RRMmLUVJjdzfKdwB")
                    .addAnnotatedClass(CheckAreaBean.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Something went wrong during initializing Hibernate: " + ex);
            throw new ExceptionInInitializerError();
        }
    }

    public static SessionFactory getFactory() {
        return factory;
    }
}
