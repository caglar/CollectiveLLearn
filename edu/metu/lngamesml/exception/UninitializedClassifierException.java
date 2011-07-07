package edu.metu.lngamesml.exception;

/**
 * Created by IntelliJ IDEA.
 * User: caglar
 * Date: 6/26/11
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class UninitializedClassifierException extends Exception{

    private String ExceptionMessage = "Uninitialized Classifier, please initialize the classifier first!!";

    public UninitializedClassifierException(String message) {
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
