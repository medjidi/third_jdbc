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
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(40), lastName VARCHAR(40), age TINYINT)");

            statement.executeUpdate();
            connection.commit();
            statement.close();


        } catch (SQLException e) {
            System.out.println("Database has not been created");
        }

    }

    public void dropUsersTable() {

        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS users");
            statement.executeUpdate();
            connection.commit();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Database has not been dropped");
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (Connection connection = Util.getConnection()) {

            String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            statement.close();
            connection.commit();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Database has not been increase");
        }
    }

    public void removeUserById(long id) {

        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = id");

            statement.executeUpdate();
            connection.commit();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Database has not been decrement");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                list.add(new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4)));
            }
            statement.close();


        } catch (SQLException e) {
            System.out.println("Database has not been read");
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("TRUNCATE users");

            statement.executeUpdate();
            connection.commit();
            statement.close();

        } catch (SQLException e) {
            System.out.println("Database has not been cleared");
        }
    }
}
