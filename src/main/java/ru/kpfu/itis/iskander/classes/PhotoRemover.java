package ru.kpfu.itis.iskander.classes;

import java.io.File;

public class PhotoRemover {

    private final String appPath;

    public PhotoRemover(String appPath) {
        this.appPath = appPath;
    }

    public boolean deletePhoto(String name) {
        File file = new File(appPath + File.separator + "pictures" + File.separator + name);
        return file.delete();
    }

}
