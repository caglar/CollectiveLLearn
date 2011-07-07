package edu.metu.lngamesml.exception;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 6/22/11
 * Time: 2:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class UnknownSigmoidFunction extends Exception {
    private String ExceptionMessage = "Unknown Sigmoid Function is entered!";

    public UnknownSigmoidFunction (String message) {
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

