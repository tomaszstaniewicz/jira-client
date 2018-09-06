package net.rcarz.utils.datetime;

import java.text.ParseException;

public class DateAndTimeParseException extends ParseException {
    public DateAndTimeParseException(String s, int errorOffset) {
        super(s, errorOffset);
    }
}
