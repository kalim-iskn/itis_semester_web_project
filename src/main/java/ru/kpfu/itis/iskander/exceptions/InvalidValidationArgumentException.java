package ru.kpfu.itis.iskander.exceptions;

public class InvalidValidationArgumentException extends Throwable {
    public InvalidValidationArgumentException() {
        super("This validation argument is invalid");
    }
}
