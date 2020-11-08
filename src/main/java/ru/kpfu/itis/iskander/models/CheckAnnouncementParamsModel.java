package ru.kpfu.itis.iskander.models;

import ru.kpfu.itis.iskander.helpers.ParametersHelper;
import ru.kpfu.itis.iskander.helpers.ValidationHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CheckAnnouncementParamsModel {

    public static ArrayList<String> check(HttpServletRequest req) {
        TreeMap<String, String[]> requirements = new TreeMap<>();
        requirements.put("name", new String[] {"trim|req|max[256]", "название объявления"});
        requirements.put("description", new String[] {"trim|req|max[5000]", "описание объявления"});
        requirements.put("city", new String[] {"trim|req|valid_city", "город"});
        requirements.put("category", new String[] {"trim|req|valid_category", "категория"});
        requirements.put("is_new", new String[] {"trim|req|boolean_int", "тип"});
        requirements.put("price", new String[] {"trim|req|intval|min[1]", "цена"});
        Map<String, String> params = ParametersHelper.trimAll(req.getParameterMap());
        return ValidationHelper.validate(params, requirements);
    }

}
