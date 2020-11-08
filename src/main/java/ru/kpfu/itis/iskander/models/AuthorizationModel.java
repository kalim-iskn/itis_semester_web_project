package ru.kpfu.itis.iskander.models;

import org.mindrot.jbcrypt.BCrypt;
import ru.kpfu.itis.iskander.database.DataBaseConnection;
import ru.kpfu.itis.iskander.classes.Settings;
import ru.kpfu.itis.iskander.exceptions.ServerProblemException;
import ru.kpfu.itis.iskander.helpers.ParametersHelper;
import ru.kpfu.itis.iskander.helpers.ValidationHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class AuthorizationModel {

    public static ArrayList<String> authenticate(HttpServletRequest req, HttpServletResponse resp) {
        TreeMap<String, String[]> requirements = new TreeMap<>();
        requirements.put("email", new String[] {"trim|req", null});
        requirements.put("password", new String[] {"trim|req", "пароль"});
        Map<String, String> params = ParametersHelper.trimAll(req.getParameterMap());
        ArrayList<String> errors = ValidationHelper.validate(params, requirements);

        if (errors.isEmpty()) {
            try {
                DataBaseConnection dbConnection = new DataBaseConnection();
                String query = "SELECT count(1) as num_rows, password, id FROM users WHERE email = ?";
                PreparedStatement statement = dbConnection.getConnection().prepareStatement(query);
                statement.setString(1, params.get("email"));
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                if (resultSet.getInt("num_rows") > 0) {
                    if (BCrypt.checkpw(params.get("password"), resultSet.getString("password"))) {
                        if (!authorize(resp, resultSet.getInt("id"), dbConnection.getConnection()))
                            errors.add("Проблемы с сервером");
                    } else {
                        errors.add("Неверный пароль");
                    }
                } else {
                    errors.add("Неверный email");
                }
                dbConnection.close();
            } catch (SQLException | ClassNotFoundException | ServerProblemException exception) {
                errors.add("Проблемы с базой данных или с сервером");
            }
        }
        return errors;
    }

    public static boolean authorize(HttpServletResponse resp, int id, Connection connection) throws SQLException, ServerProblemException {
        String token = getToken();
        String query = "INSERT INTO tokens (token, user_id) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, token);
        statement.setInt(2, id);
        if (statement.executeUpdate() == 1) {
            Settings settings = new Settings();
            int tokenValidity = Integer.parseInt(settings.get("token_validity_days")) * 24 * 3600;

            Cookie tokenCookie = new Cookie("token", token);
            Cookie idCookie = new Cookie("id", id + "");

            tokenCookie.setPath("/");
            idCookie.setPath("/");
            tokenCookie.setMaxAge(tokenValidity);
            idCookie.setMaxAge(tokenValidity);

            resp.addCookie(tokenCookie);
            resp.addCookie(idCookie);
            return true;
        } else {
            return false;
        }
    }

    private static String getToken() {
        return UUID.randomUUID().toString();
    }

}
