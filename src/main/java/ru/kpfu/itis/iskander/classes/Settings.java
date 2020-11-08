package ru.kpfu.itis.iskander.classes;

import ru.kpfu.itis.iskander.exceptions.ServerProblemException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Settings {

    Properties property = new Properties();

    public Settings() throws ServerProblemException {
        try {
            InputStream fis = getClass().getClassLoader().getResourceAsStream("settings.properties");
            if (fis != null) {
                property.load(new InputStreamReader(fis, StandardCharsets.UTF_8));
                fis.close();
            } else {
                throw new ServerProblemException();
            }
        } catch (IOException e) {
            throw new ServerProblemException();
        }
    }

    public String get(String name) {
        return property != null ? property.getProperty(name) : null;
    }

    public String[] getArray(String name) {return property != null ? property.getProperty(name).split(",") : null;}

}
