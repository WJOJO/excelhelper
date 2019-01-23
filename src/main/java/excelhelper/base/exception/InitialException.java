package excelhelper.base.exception;

/**
 * @author Javon Wang
 * @description 初始化失败
 * @create 2019-01-04 13:50
 */
public class InitialException extends RuntimeException {

    public InitialException() {
    }

    public InitialException(String message) {
        super(message);
    }

    public InitialException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitialException(Throwable cause) {
        super(cause);
    }

    public InitialException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
