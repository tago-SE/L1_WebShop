package model.handlers.exceptions;

public class RegisterException extends Exception {

    public final int code;

    public RegisterException(int code) {
        super();
        this.code = code;
    }

}