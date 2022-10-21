package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.fasterxml.classmate.AnnotationConfiguration;

import java.util.Properties;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Util {
    private static volatile Connection connection;
    private static volatile SessionFactory sessionFactory;

    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest?autoReconnect=true&useSSL=false";
    private static final String Driver = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static SessionFactory getSessionFactory() {
        SessionFactory localSession = sessionFactory;

        if (localSession == null || localSession.isClosed()) {

            synchronized (Util.class) {
                localSession = sessionFactory;

                try {
                    Configuration configuration = new Configuration();
                    Properties settings = new Properties();
                    
                    settings.put(Environment.DRIVER, Driver);
                    settings.put(Environment.URL, URL);
                    settings.put(Environment.USER, USERNAME);
                    settings.put(Environment.PASS, PASSWORD);
                    settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                    settings.put(Environment.SHOW_SQL, "true");

                    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                    settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                    configuration.setProperties(settings);

                    configuration.addAnnotatedClass(User.class);

                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();

                    sessionFactory = localSession = configuration.buildSessionFactory(serviceRegistry);

                } catch (Exception e) {
                    System.out.println("Hibernate Connection Error!!!");
                }
            }

        }
        return localSession;

    }


    public static Connection getConnection() {

        Connection localConnection = connection;

        try {
            if (localConnection == null || localConnection.isClosed()) {

                synchronized (Util.class) {
                    localConnection = connection;

                    try {
                        Class.forName(Driver);
                        connection = localConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                        connection.setAutoCommit(false);

                    } catch (ClassNotFoundException | SQLException e) {
                        System.out.println("Connection Error!!!");
                    }

                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return localConnection;

    }

}
