package ru.kpfu.itis.iskander.helpers;

import ru.kpfu.itis.iskander.exceptions.ServerProblemException;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class ListHelper {

    ArrayList<String> list;

    public ListHelper(String type) throws IOException, ServerProblemException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(type + ".txt");
        list = new ArrayList<>();
        if (resource != null) {
            try {
                File file = new File(resource.toURI());
                Path path = file.toPath();
                BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                String line = reader.readLine();
                while (line != null) {
                    list.add(line);
                    line = reader.readLine();
                }
                reader.close();
            } catch (URISyntaxException ignore) {
                throw new ServerProblemException();
            }
        } else {
            throw new ServerProblemException();
        }
    }

    public ArrayList<String> getList() {
        return list;
    }

    public boolean isExist(String city) {
        return list.contains(city);
    }

}
