package ru.kpfu.itis.iskander.models;

import ru.kpfu.itis.iskander.database.DataBaseConnection;
import ru.kpfu.itis.iskander.classes.Settings;
import ru.kpfu.itis.iskander.exceptions.ServerProblemException;
import ru.kpfu.itis.iskander.helpers.CookieHelper;
import ru.kpfu.itis.iskander.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class CheckAuthorizationModel {

    public static int check(HttpServletRequest request) throws ServerProblemException {
        CookieHelper cookies = new CookieHelper(request);
        Cookie tokenCookie = cookies.search("token");
        Cookie idCookie = cookies.searchInt("id");

        if (tokenCookie == null || idCookie == null)
            return -1;

        String token = tokenCookie.getValue();
        int id = Integer.parseInt(idCookie.getValue());

        try {
            UserRepository userRepository = new UserRepository();
            boolean isUserExist = userRepository.isExist(id);
            userRepository.closeConnection();
            if (isUserExist) {
                Settings settings = new Settings();
                int tokenValidityDays = Integer.parseInt(settings.get("token_validity_days"));
                long tokenValidTime = Instant.now().getEpochSecond() - tokenValidityDays * 24 * 3600;
                DataBaseConnection dbConnection = new DataBaseConnection();
                String query = "SELECT count(1) AS num_rows FROM tokens WHERE token = ? AND user_id = ? AND " +
                        "UNIX_TIMESTAMP(created_at) > ?";
                PreparedStatement statement = dbConnection.getConnection().prepareStatement(query);
                statement.setString(1, token);
                statement.setInt(2, id);
                statement.setLong(3, tokenValidTime);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                int numRows = resultSet.getInt("num_rows");
                dbConnection.close();
                return id;
            } else {
                return -1;
            }
        } catch (SQLException | ClassNotFoundException e) {
            return -1;
        }
    }

}
