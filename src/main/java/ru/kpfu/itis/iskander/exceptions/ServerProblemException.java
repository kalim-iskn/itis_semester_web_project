package ru.kpfu.itis.iskander.exceptions;

public class ServerProblemException extends Throwable {

    public ServerProblemException() {
        super("Problems with server");
    }

}
