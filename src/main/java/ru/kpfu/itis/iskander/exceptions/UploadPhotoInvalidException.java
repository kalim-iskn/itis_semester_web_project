package ru.kpfu.itis.iskander.exceptions;

public class UploadPhotoInvalidException extends Throwable {

    public UploadPhotoInvalidException() {
        super("The uploaded photo does not meet the requirements");
    }

}
