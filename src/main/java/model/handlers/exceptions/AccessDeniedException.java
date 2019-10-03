package model.handlers.exceptions;

public class AccessDeniedException extends Exception {

    public AccessDeniedException(Exception e) {
        super(e);
    }

    public AccessDeniedException(String s) {
        super(s);
    }

    public AccessDeniedException() {
        super();
    }

}
