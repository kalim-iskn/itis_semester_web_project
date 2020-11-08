package ru.kpfu.itis.iskander.models;

import ru.kpfu.itis.iskander.classes.User;
import ru.kpfu.itis.iskander.exceptions.NoSuchRecordIntoTableException;
import ru.kpfu.itis.iskander.helpers.ParametersHelper;
import ru.kpfu.itis.iskander.helpers.ValidationHelper;
import ru.kpfu.itis.iskander.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class EditProfileModel {

    public static ArrayList<String> edit(HttpServletRequest req) throws SQLException, NoSuchRecordIntoTableException, ClassNotFoundException {
        int id = (int) req.getAttribute("authorizedUserId");
        UserRepository userRepository = new UserRepository();
        User user = userRepository.getById(id);

        TreeMap<String, String[]> requirements = new TreeMap<>();
        requirements.put("name", new String[] {"trim|req|max[32]", "имя"});
        requirements.put("surname", new String[] {"trim|req|max[32]", "фамилия"});
        requirements.put("city", new String[] {"trim|req|max[32]|valid_city", "город"});
        Map<String, String> params = ParametersHelper.trimAll(req.getParameterMap());
        ArrayList<String> errors = ValidationHelper.validate(params, requirements);

        if (errors.isEmpty()) {
            user.setCity(req.getParameter("city"));
            user.setName(req.getParameter("name"));
            user.setSurname(req.getParameter("surname"));
            if (!userRepository.updateInformation(user))
                errors.add("Проблемы с сервером");
        }
        return errors;
    }

}
