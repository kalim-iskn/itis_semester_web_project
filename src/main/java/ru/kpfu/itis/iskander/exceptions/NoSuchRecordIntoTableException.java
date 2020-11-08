package ru.kpfu.itis.iskander.exceptions;

public class NoSuchRecordIntoTableException extends Throwable{
    public NoSuchRecordIntoTableException() {
        super("This record was not found in the table");
    }
}
