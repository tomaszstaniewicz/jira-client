package net.rcarz.utils.datetime;

import java.text.ParseException;

public class TimeParseException extends ParseException {
    public TimeParseException(String s, int errorOffset) {
        super(s, errorOffset);
    }
}
