package ru.kpfu.itis.iskander.repositories;

import ru.kpfu.itis.iskander.classes.SaleAnnouncement;
import ru.kpfu.itis.iskander.database.DataBaseConnection;
import ru.kpfu.itis.iskander.exceptions.NoSuchRecordIntoTableException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SaleAnnouncementRepository {

    private Connection connection;

    public SaleAnnouncementRepository() throws SQLException, ClassNotFoundException {
        DataBaseConnection dbConnection = new DataBaseConnection();
        connection = dbConnection.getConnection();
    }

    public SaleAnnouncement getById(int id) throws SQLException, NoSuchRecordIntoTableException {
        String query = "SELECT *, count(1) AS num_rows FROM announcements WHERE id = ? LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        if (resultSet.getInt("num_rows") == 0)
            throw new NoSuchRecordIntoTableException();
        return getAnnouncement(resultSet);
    }

    public ArrayList<SaleAnnouncement> getAllByUser(int authorId) throws SQLException {
        String query = "SELECT * FROM announcements WHERE author_id = ? ORDER BY id DESC";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, authorId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<SaleAnnouncement> announcements = new ArrayList<>();
        while (resultSet.next()) {
            announcements.add(getAnnouncement(resultSet));
        }
        return announcements;
    }

    public boolean isExist(int id) throws SQLException {
        String query = "SELECT *, count(1) AS num_rows FROM announcements WHERE id = ? LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt("num_rows") == 1;
    }

    private SaleAnnouncement getAnnouncement(ResultSet resultSet) throws SQLException {
        return new SaleAnnouncement(
                resultSet.getInt("id"), resultSet.getInt("author_id"),
                resultSet.getString("name"), resultSet.getString("description"),
                resultSet.getTimestamp("created_at"), resultSet.getString("city"),
                resultSet.getString("category"), resultSet.getBoolean("is_new"),
                resultSet.getString("main_picture"), resultSet.getString("pictures"),
                resultSet.getInt("price")
        );
    }

    public String getMainPicture(int id) throws SQLException, NoSuchRecordIntoTableException {
        String query = "SELECT main_picture, count(1) AS num_rows FROM announcements WHERE id = ? LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        if (resultSet.getInt("num_rows") == 0)
            throw new NoSuchRecordIntoTableException();
        return resultSet.getString("main_picture");
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public boolean delete(int id, int authorizedUserId) throws SQLException {
        String query = "DELETE FROM announcements WHERE id = ? AND author_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setInt(2, authorizedUserId);
        return statement.executeUpdate() == 1;
    }

    public ArrayList<SaleAnnouncement> getFromCategory(String category) throws SQLException {
        String query = "SELECT * FROM announcements WHERE category = ? ORDER BY id DESC LIMIT 3";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, category);
        ResultSet resultSet = statement.executeQuery();
        return getCollection(resultSet);
    }

    //TO DO offset
    public ArrayList<SaleAnnouncement> getWithParameters(String name, String city, String category, int[] prices) throws SQLException {
        String query = "SELECT * FROM announcements WHERE name LIKE ? AND city LIKE ? AND category LIKE ? AND price >= ?";
        if (prices[1] != -1)
            query += " AND price <= ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + name + "%");
        statement.setString(2, city.equals("") ? "%" : city);
        statement.setString(3, category.equals("") ? "%" : category);
        statement.setInt(4, prices[0] == -1 ? 0 : prices[0]);
        if (prices[1] != -1)
            statement.setInt(5, prices[1]);
        ResultSet resultSet = statement.executeQuery();
        return getCollection(resultSet);
    }

    private ArrayList<SaleAnnouncement> getCollection(ResultSet resultSet) throws SQLException {
        ArrayList<SaleAnnouncement> saleAnnouncements = new ArrayList<>();
        while (resultSet.next()) {
            saleAnnouncements.add(new SaleAnnouncement(
                    resultSet.getInt("id"), resultSet.getInt("author_id"),
                    resultSet.getString("name"), resultSet.getString("description"),
                    resultSet.getTimestamp("created_at"), resultSet.getString("city"),
                    resultSet.getString("category"), resultSet.getBoolean("is_new"),
                    resultSet.getString("main_picture"), resultSet.getString("pictures"),
                    resultSet.getInt("price")
            ));
        }

        return saleAnnouncements.isEmpty() ? null : saleAnnouncements;
    }

}
