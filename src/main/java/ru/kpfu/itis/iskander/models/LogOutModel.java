package ru.kpfu.itis.iskander.models;

import ru.kpfu.itis.iskander.database.DataBaseConnection;
import ru.kpfu.itis.iskander.helpers.CookieHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogOutModel {

    public static void logout(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException {
        CookieHelper cookies = new CookieHelper(req);
        Cookie token = cookies.search("token");
        Cookie id = cookies.searchInt("id");

        DataBaseConnection dbConnection = new DataBaseConnection();
        Connection connection = dbConnection.getConnection();
        String query = "DELETE FROM tokens WHERE user_id = ? AND token = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, Integer.parseInt(id.getValue()));
        statement.setString(2, token.getValue());

        if (statement.executeUpdate() == 1) {
            id.setMaxAge(0);
            token.setMaxAge(0);

            id.setPath("/");
            token.setPath("/");

            resp.addCookie(id);
            resp.addCookie(token);
        }
        dbConnection.close();

    }

}
