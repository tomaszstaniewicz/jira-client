package net.rcarz.jiraclient;

public class NoSuchAllowedValueException extends JiraException {

    public NoSuchAllowedValueException(String msg) {
        super(msg);
    }

    public NoSuchAllowedValueException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
