package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(40), lastName VARCHAR(40), age TINYINT)").executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Database has not been created");
        }

    }

    public void dropUsersTable() {

        try (Connection connection = Util.getConnection()) {
            connection.createStatement().executeUpdate("DROP TABLE IF EXISTS users");
            connection.commit();

        } catch (SQLException e) {
            System.out.println("Database has not been dropped");
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement statement = Util.getConnection().prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            statement.getConnection().commit();
        } catch (SQLException e) {
            System.out.println("Database has not been increase");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement("DELETE FROM users WHERE id = id");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Database has not been cleared");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (ResultSet resultSet = Util.getConnection().prepareStatement("SELECT * FROM users").executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                list.add(new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4)));
            }
        } catch (SQLException e) {
            System.out.println("Database has not been read");
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement("TRUNCATE users").executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Database has not been cleared");
        }
    }
}
