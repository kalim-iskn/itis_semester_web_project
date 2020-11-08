package ru.kpfu.itis.iskander.repositories;

import ru.kpfu.itis.iskander.classes.User;
import ru.kpfu.itis.iskander.database.DataBaseConnection;
import ru.kpfu.itis.iskander.exceptions.NoSuchRecordIntoTableException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    private Connection connection;

    public UserRepository() throws SQLException, ClassNotFoundException {
        DataBaseConnection dbConnection = new DataBaseConnection();
        connection = dbConnection.getConnection();
    }

    public boolean updateInformation(User user) throws SQLException, NoSuchRecordIntoTableException {
        if (isExist(user.getId())) {
            String query = "UPDATE users SET name = ?, surname = ?, city = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getCity());
            statement.setInt(4, user.getId());
            return statement.executeUpdate() == 1;
        } else {
            throw new NoSuchRecordIntoTableException();
        }
    }

    public User getById(int id) throws SQLException, NoSuchRecordIntoTableException {
        String query = "SELECT *, count(1) AS num_rows FROM users WHERE id = ? LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        if (resultSet.getInt("num_rows") == 0)
            throw new NoSuchRecordIntoTableException();
        return new User(
                resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getString("surname"),  resultSet.getString("email"),
                resultSet.getString("city"), resultSet.getString("password"),
                resultSet.getTimestamp("registered_at")
        );
    }

    public boolean isExist(int id) throws SQLException {
        String query = "SELECT count(1) AS num_rows FROM users WHERE id = ? LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt("num_rows") == 1;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

}
