package ru.kpfu.itis.iskander.helpers;

import java.util.HashMap;
import java.util.Map;

public class ParametersHelper {

    public static Map<String, String> trimAll(Map<String, String[]> params) {
        Map<String, String> trimParams = new HashMap<>();
        params.forEach((k, v) -> trimParams.put(k, v[0].trim()));
        return trimParams;
    }

}
