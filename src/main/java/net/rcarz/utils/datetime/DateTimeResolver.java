package net.rcarz.utils.datetime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class DateTimeResolver {

    private static final List<Pair<String, String>> DATE_FORMAT_REGEXPS = new ArrayList<>();
    private static final List<Pair<String, String>> TIME_FORMAT_REGEXPS = new ArrayList<>();
    private static final List<Pair<String, String>> DATE_TIME_SEPARATORS = new ArrayList<>();

    static {
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}\\.\\d{1,2}\\.\\d{4}$", "dd.MM.yyyy"));
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd"));
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy"));
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd"));
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy"));
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}\\s[a-zA-Z]{4,}\\s\\d{4}$", "dd MMMM yyyy"));
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^[a-zA-Z]{3}\\s\\d{1,2}\\s\\d{4}$", "MMM dd yyyy"));
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{4}\\s[a-zA-Z]{3}\\s\\d{1,2}$", "yyyy MMM dd"));
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}[a-zA-Z]{3}\\d{2}$", "ddMMMyy"));//
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}[a-zA-Z]{3}\\d{4}$", "ddMMMyyyy"));//
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}\\s[a-zA-Z]{3}\\s\\d{4}$", "dd MMM yyyy"));
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{2}-\\d{1,2}-\\d{1,2}$", "yy-MM-dd"));
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{8}$", "yyyyMMdd"));
        DATE_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}\\.\\d{1,2}\\.\\d{2}$", "dd.MM.yy"));
    }

    static {
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}$", "HH:mm"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}:\\d{2}$", "HH:mm:ss"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}:\\d{2}\\.\\d{2}$", "HH:mm:ss.SS"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}:\\d{2}\\.\\d{3}$", "HH:mm:ss.SSS"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}:\\d{2}\\.\\d{2}[-+]\\d{2}:\\d{2}$", "HH:mm:ss.SSSXXX"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}:\\d{2}\\.\\d{3}[-+]\\d{2}:\\d{2}$", "HH:mm:ss.SSXXX"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}:\\d{2}\\.\\d{3}[-+]\\d{4}$", "HH:mm:ss.SSSZ"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}:\\d{2}\\.\\d{2}[-+]\\d{4}$", "HH:mm:ss.SSZ"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}:\\d{2}[-+]\\d{2}:\\d{2}$", "HH:mm:ssXXX"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}:\\d{2}[-+]\\d{4}$", "HH:mm:ssZ"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}[-+]\\d{2}:\\d{2}$", "HH:mmXXX"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}[-+]\\d{4}$", "HH:mmZ"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{4}(PM|AM|pm|am)$", "hhmma"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{4}\\s(PM|AM|pm|am)$", "hhmm a"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}(PM|AM|pm|am)$", "hh:mma"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}:\\d{2}(PM|AM|pm|am)$", "hh:mm:ssa"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2} (PM|AM|pm|am)$", "hh:mm a"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{1,2}:\\d{2}:\\d{2} (PM|AM|pm|am)$", "hh:mm:ss a"));
        TIME_FORMAT_REGEXPS.add(new ImmutablePair<>("^\\d{6}$", "HHmmss"));
    }

    static {
        DATE_TIME_SEPARATORS.add(new ImmutablePair<>("\\s", " "));
        DATE_TIME_SEPARATORS.add(new ImmutablePair<>("T", "'T'"));
        DATE_TIME_SEPARATORS.add(new ImmutablePair<>("t", "'t'"));
        DATE_TIME_SEPARATORS.add(new ImmutablePair<>("", ""));
    }

    /**
     * transforms given dateTime as a string to java Date object
     *
     * @param dateTimeAsString dateTime to be parsed
     * @return parsed date
     * @throws DateTimeParseException in case when parameter can't be parsed
     */
    public Date resolveDateTime(String dateTimeAsString) throws ParseException {
        for (Pair<String, String> dateEntry : DATE_FORMAT_REGEXPS) {
            for (Pair<String, String> timeEntry : TIME_FORMAT_REGEXPS) {
                for (Pair<String, String> dateTimeSeparatorEntry : DATE_TIME_SEPARATORS) {
                    String dateTimeFormatRegex = dateEntry.getKey().substring(0, dateEntry.getKey().length() - 1)
                            + dateTimeSeparatorEntry.getKey() + timeEntry.getKey().substring(1, timeEntry.getKey().length());

                    if (dateTimeAsString.matches(dateTimeFormatRegex)) {
                        String dateTimeFormatPattern = dateEntry.getValue() + dateTimeSeparatorEntry.getValue()
                                + timeEntry.getValue();

                        DateFormat df = new SimpleDateFormat(dateTimeFormatPattern, Locale.US);
                        df.setTimeZone(TimeZone.getTimeZone("UTC"));
                       return df.parse(dateTimeAsString);                        
                    }
                }
            }
        }

        throw new DateTimeParseException("Problem with parsing date_time: " + dateTimeAsString, 1);
    }

    /**
     * transforms given date and time as a string to java Date object
     *
     * @param dateAsString date to be parsed
     * @param timeAsString time to be parsed
     * @return parsed date
     * @throws DateAndTimeParseException in case when both dateAsString and timeAsString can't be parsed
     * @throws DateParseException in case when only dateAsString can't be parsed
     * @throws TimeParseException in case when only timeAsString can't be parsed
     */
    public Date resolveDateTime(String dateAsString, String timeAsString) throws ParseException {
        Optional<String> dateFormatPattern = determineDateFormatPattern(dateAsString);
        Optional<String> timeFormatPattern = determineTimeFormatPattern(timeAsString);

        if (dateFormatPattern.isPresent() && timeFormatPattern.isPresent()) {
            String dateTimeFormatPattern = dateFormatPattern.get() + timeFormatPattern.get();
            DateFormat df = new SimpleDateFormat(dateTimeFormatPattern, Locale.US);
            df.setTimeZone(TimeZone.getTimeZone("UTC"));

            String dateTimeAsString = dateAsString + timeAsString;
            return df.parse(dateTimeAsString);
        } else if (!dateFormatPattern.isPresent() && !timeFormatPattern.isPresent()) {
            throw new DateAndTimeParseException("Problem with parsing date: " + dateAsString + " and time: " + timeAsString, 1);
        } else if (!dateFormatPattern.isPresent()) {
            throw new DateParseException("Problem with parsing date: " + dateAsString, 1);
        } else if (!timeFormatPattern.isPresent()) {
            throw new TimeParseException("Problem with parsing time: " + timeAsString, 1);
        }

        throw new ParseException("Problem with parsing date_time: ", 2);
    }

    private Optional<String> determineDateFormatPattern(String dateString) {
        for (Pair<String, String> regexpEntry : DATE_FORMAT_REGEXPS) {
            if (dateString.matches(regexpEntry.getKey())) {
                return Optional.of(regexpEntry.getValue());
            }
        }
        return Optional.empty(); // Unknown format.
    }

    private Optional<String> determineTimeFormatPattern(String timeString) {
        for (Pair<String, String> regexpEntry : TIME_FORMAT_REGEXPS) {
            if (timeString.matches(regexpEntry.getKey())) {
                return Optional.of(regexpEntry.getValue());
            }
        }
        return Optional.empty(); // Unknown format.
    }
}