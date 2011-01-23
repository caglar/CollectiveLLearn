package edu.metu.lngamesml.exception;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: Oct 31, 2010
 * Time: 8:41:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class FaultyInputException extends Exception {

    private String ExceptionMessage = "Illegal input is detected!";

    public FaultyInputException (String message) {
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
