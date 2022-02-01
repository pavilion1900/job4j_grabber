package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static java.util.Map.entry;

public class SqlRuDateTimeParser implements DateTimeParser {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d MM yy");
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("d MM yy, HH:mm");
    private static final String TODAY = "сегодня";
    private static final String YESTERDAY = "вчера";
    private static final Map<String, String> MONTHS = Map.ofEntries(
            entry("янв", "01"), entry("фев", "02"), entry("мар", "03"),
            entry("апр", "04"), entry("май", "05"), entry("июн", "06"),
            entry("июл", "07"), entry("авг", "08"), entry("сен", "09"),
            entry("окт", "10"), entry("ноя", "11"), entry("дек", "12")
    );

    @Override
    public LocalDateTime parse(String parse) {
        if (parse.contains(TODAY)) {
            parse = parse.replace(TODAY, LocalDateTime.now().format(DATE_FORMATTER));
        } else if (parse.contains(YESTERDAY)) {
            parse = parse.replace(YESTERDAY,
                    LocalDateTime.now().minusDays(1).format(DATE_FORMATTER));
        } else {
            String month = parse.split(" ")[1];
            parse = parse.replace(month, MONTHS.get(month));
        }
        return LocalDateTime.parse(parse, DATE_TIME_FORMATTER);
    }
}
