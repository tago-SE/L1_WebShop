package model;

public class Result {

    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;
    public static final int EXCEPTION = -1;

    public Integer status;
    public String message;
    public Object data;

    public Result() { }

    public Result(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
