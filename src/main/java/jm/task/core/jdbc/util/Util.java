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

    private static volatile Util instance;
    private Connection connection;

    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest?autoReconnect=true&useSSL=false";
    private static final String Driver = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Util() {

    }

    public static Util getInstance() {
        if (instance == null) {
            synchronized (Util.class) {
                if (instance == null) {
                    instance = new Util();
                }
            }
        }
        return instance;
    }


    public  Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                synchronized (Util.class) {
                    try {
                        Class.forName(Driver);
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                        connection.setAutoCommit(false);

                    } catch (ClassNotFoundException | SQLException e) {
                        System.out.println("Connection Error!!!");
                        e.printStackTrace();
                    }

                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
