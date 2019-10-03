package model.handlers.exceptions;

public class OutOfStockException extends IllegalStateException {
    public OutOfStockException(Exception e) {
        super(e);
    }

    public OutOfStockException(String s) {
        super(s);
    }

    public OutOfStockException() {
        super();
    }
}
