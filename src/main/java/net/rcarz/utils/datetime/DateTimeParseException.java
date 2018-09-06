package net.rcarz.utils.datetime;

import java.text.ParseException;

public class DateTimeParseException extends ParseException {
    public DateTimeParseException(String s, int errorOffset) {
        super(s, errorOffset);
    }
}
