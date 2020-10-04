package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    String query = "";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

        query = "CREATE TABLE IF NOT EXISTS users( id BIGINT(8) auto_increment, " +
                "name varchar (45) not null, " +
                "lastname varchar(45) not null," +
                "age int null, constraint id_UNIQUE unique(id));";

        String additionalQuery = "alter table users add primary key (id)";

        try (Connection connection = Util.getDBConnection(); Statement st = connection.createStatement()) {
            st.execute(query);
            st.executeUpdate(additionalQuery);
        } catch (SQLException throwables) {
            //just return if exist
        }
    }

    public void dropUsersTable() {

        query = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getDBConnection();
             Statement st = connection.createStatement()) {
            st.executeUpdate(query);
        } catch (SQLException throwables) {
            System.err.println("Table dropping failed.");
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        query = "INSERT INTO users (name,lastname,age) VALUES(?,?,?);";

        try (Connection connection = Util.getDBConnection(); PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            System.err.println("Saving of user failed");
        }

    }

    public void removeUserById(long id) {
        query = "DELETE from users WHERE id = ?";
        try (Connection connection = Util.getDBConnection(); PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to delete by id " + id);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        query = "SELECT name, lastname, age, id FROM users";
        try (Connection connection = Util.getDBConnection(); Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                User usr = new User(rs.getString(1), rs.getString(2), (byte) rs.getInt(3));
                usr.setId(rs.getLong(4));
                users.add(usr);
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState());
        }
        return users;
    }

    public void cleanUsersTable() {
        query = "DELETE FROM users";
        try (Connection connection = Util.getDBConnection(); Statement st = connection.createStatement()) {
            st.executeUpdate(query);
        } catch (SQLException s) {
            System.err.println("Users deletion failed");
        }
    }
}
