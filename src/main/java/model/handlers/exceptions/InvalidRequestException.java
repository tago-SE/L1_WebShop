package model.handlers.exceptions;

public class InvalidRequestException extends IllegalArgumentException {

    public InvalidRequestException(Exception e) {
        super(e);
    }

    public InvalidRequestException(String s) {
        super(s);
    }

    public InvalidRequestException() {
        super();
    }
}
