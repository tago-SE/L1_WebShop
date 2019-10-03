package model.handlers.exceptions;

public class DatabaseException extends Exception {

    public DatabaseException(Exception e) {
        super(e);
    }

    public DatabaseException(String s) {
        super(s);
    }

    public DatabaseException() {
        super();
    }
}
