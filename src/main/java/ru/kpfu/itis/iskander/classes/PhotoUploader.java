package ru.kpfu.itis.iskander.classes;

import org.apache.commons.codec.digest.DigestUtils;
import ru.kpfu.itis.iskander.exceptions.UploadPhotoInvalidException;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class PhotoUploader {

    private final int MAX_SIZE;
    private final String appPath;

    public PhotoUploader(String appPath) {
        MAX_SIZE = 3 * 1024 * 1024;
        this.appPath = appPath;
    }

    public String upload(Part filePart) throws IOException, UploadPhotoInvalidException {
        if (filePart.getContentType().equals("image/jpeg") && filePart.getSize() <= MAX_SIZE) {
            String savePath = appPath + File.separator + "pictures";
            String fileName =
                    DigestUtils.md5Hex(
                            Paths.get(filePart.getSubmittedFileName()).getFileName().toString() +
                                    System.currentTimeMillis()
                    ) + ".jpg";
            fileName = new File(fileName).getName();
            filePart.write(savePath + File.separator + fileName);
            return fileName;
        } else {
            throw new UploadPhotoInvalidException();
        }
    }

}
