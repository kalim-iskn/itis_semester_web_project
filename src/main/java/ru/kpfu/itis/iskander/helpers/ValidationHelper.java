package ru.kpfu.itis.iskander.helpers;

import ru.kpfu.itis.iskander.exceptions.ServerProblemException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {

    public static ArrayList<String> validate(Map<String, String> params, Map<String, String[]> requirements) {
        ArrayList<String> errors = new ArrayList<>();
        requirements.forEach((name, requirementsForCurrent) -> {
            String result = check(name, params.get(name), requirementsForCurrent[0], requirementsForCurrent[1]);
            if (result != null)
                errors.add(result);
        });
        return errors;
    }

    private static String check(String name, String param, String allRequirements, String customName) {
        if (allRequirements.charAt(allRequirements.length() - 1) != '|')
            allRequirements += "|";
        Pattern pattern = Pattern.compile("([^|]*)\\|");
        Matcher matcher = pattern.matcher(allRequirements);
        String nameForError = customName == null ? name : customName;
        while (matcher.find()) {
            String requirement = allRequirements.substring(matcher.start(), matcher.end() - 1);
            switch (requirement) {
                case "req":
                    if (param.equals(""))
                        return "Поле " + nameForError + " обязательно к заполнению";
                    break;
                case "intval":
                    try {
                        Integer.parseInt(param);
                    } catch (NumberFormatException e) {
                        return "Поле " + nameForError + " должно быть в числовом формате";
                    }
                    break;
                case "boolean_int":
                    try {
                        int x = Integer.parseInt(param);
                        if (x != 0 && x != 1)
                            return "Поле " + nameForError + " невалидное";
                    } catch (NumberFormatException e) {
                        return "Поле " + nameForError + " должно быть в числовом формате";
                    }
                    break;
                case "valid_email":
                    if (!param.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"))
                        return "Поле " + nameForError + " должно быть валидным адресом email";
                    break;
                case "valid_city":
                    String res = checkLists("cities", param, nameForError);
                    if (res != null)
                        return res;
                    break;
                case "valid_category":
                    res = checkLists("categories", param, nameForError);
                    if (res != null)
                        return res;
                    break;
                default:
                    if (requirement.matches("^(min|max)\\[([0-9]*)]$")) {
                        res = checkLength(requirement, param, nameForError);
                        if (res != null)
                            return res;
                    }
            }
        }
        return null;
    }

    private static String checkLists(String type, String param, String nameForError) {
        try {
            ListHelper listHelper = new ListHelper(type);
            if (!listHelper.isExist(param))
                return "Поле " + nameForError + " содержит неподдерживаемое системой наименование " +
                        (type.equals("cities") ? "города" : "категории");
        } catch (IOException | ServerProblemException e) {
            return "Проблемы с сервером";
        }
        return null;
    }

    private static String checkLength(String requirement, String param, String nameForError) {
        String type = requirement.substring(0, 3);
        try {
            int limit = Integer.parseInt(requirement.substring(4, requirement.length() - 1));
            double value;
            String extra = "";
            try {
                value = Double.parseDouble(param);
            } catch (NumberFormatException e) {
                value = param.length();
                extra = " в длину";
            }
            if (type.equals("max")) {
                if (value > limit)
                    return "Поле " + nameForError + " должно быть не больше чем " + limit + extra;
            } else if (value < limit) {
                return "Поле " + nameForError + " должно быть не меньше чем " + limit + extra;
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
        return null;
    }

}
