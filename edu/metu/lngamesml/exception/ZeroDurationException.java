package edu.metu.lngamesml.exception;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 12/30/10
 * Time: 11:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZeroDurationException extends Exception {

    private String ExceptionMessage = "Duration is zero!";

    public ZeroDurationException (String message) {
        super(message);
        if (message != null && !message.isEmpty()) {
            ExceptionMessage = message;
        }
    }

    public String getExceptionMessage() {
        return ExceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        ExceptionMessage = exceptionMessage;
    }
}
