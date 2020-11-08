package ru.kpfu.itis.iskander.models;

import org.mindrot.jbcrypt.BCrypt;
import ru.kpfu.itis.iskander.database.DataBaseConnection;
import ru.kpfu.itis.iskander.exceptions.ServerProblemException;
import ru.kpfu.itis.iskander.helpers.ParametersHelper;
import ru.kpfu.itis.iskander.helpers.ValidationHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;

public class RegistrationModel {

    public static ArrayList<String> register(HttpServletRequest req, HttpServletResponse resp) {
        TreeMap<String, String[]> requirements = new TreeMap<>();
        requirements.put("email", new String[] {"trim|req|valid_email", null});
        requirements.put("name", new String[] {"trim|req|max[32]", "имя"});
        requirements.put("surname", new String[] {"trim|req|max[32]", "фамилия"});
        requirements.put("city", new String[] {"trim|req|max[32]|valid_city", "город"});
        requirements.put("password", new String[] {"trim|req|min[6]", "пароль"});
        requirements.put("re_password", new String[] {"trim|req|min[6]", "повторите пароль"});
        Map<String, String> params = ParametersHelper.trimAll(req.getParameterMap());
        ArrayList<String> errors = ValidationHelper.validate(params, requirements);

        if (errors.isEmpty()) {
            if (params.get("password").equals(params.get("re_password"))) {
                try {
                    DataBaseConnection dbConnection = new DataBaseConnection();
                    if (isUniqueEmail(params.get("email"), dbConnection.getConnection())) {
                        String query = "INSERT INTO users (name, surname, email, password, city) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement statement =
                                dbConnection.getConnection().prepareStatement(query,  Statement.RETURN_GENERATED_KEYS);
                        statement.setString(1, params.get("name"));
                        statement.setString(2, params.get("surname"));
                        statement.setString(3, params.get("email"));
                        statement.setString(4, getHash(params.get("password")));
                        statement.setString(5, params.get("city"));
                        if (statement.executeUpdate() == 1) {
                            ResultSet generated = statement.getGeneratedKeys();
                            generated.next();
                            int id = generated.getInt(1);
                            if (!AuthorizationModel.authorize(resp, id, dbConnection.getConnection()))
                                errors.add("Пожалуйста, авторизуйтесь через страницу авторизации");
                        } else {
                            errors.add("Проблемы с базой данных");
                        }
                    } else {
                        errors.add("Адрес email уже принадлежит другому пользователю");
                    }
                    dbConnection.close();
                } catch (SQLException | ClassNotFoundException | ServerProblemException exception) {
                    errors.add("Проблемы с базой данных или с сервером");
                }
            } else {
                errors.add("Пароли не совпадают");
            }
        }

        return errors;
    }

    private static boolean isUniqueEmail(String email, Connection connection) throws SQLException {
        String query = "SELECT count(1) AS num_rows FROM users WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt("num_rows") == 0;
    }

    private static String getHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
