package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static volatile Connection connection;
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String Driver = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {

        Connection localConnection = connection;

        try {
            if (localConnection == null || localConnection.isClosed() ) {

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
