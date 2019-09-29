package model.handlers.exceptions;

public class LoginException extends Exception {

    public final int code;

    public LoginException(int code) {
        super();
        this.code = code;
    }

}
