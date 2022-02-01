package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static java.util.Map.entry;

public class SqlRuDateTimeParser implements DateTimeParser {
    private static final Map<String, String> MONTHS = Map.ofEntries(
            entry("янв", "01"), entry("фев", "02"), entry("мар", "03"),
            entry("апр", "04"), entry("май", "05"), entry("июн", "06"),
            entry("июл", "07"), entry("авг", "08"), entry("сен", "09"),
            entry("окт", "10"), entry("ноя", "11"), entry("дек", "12")
    );

    @Override
    public LocalDateTime parse(String parse) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MM yy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MM yy, HH:mm");
        if (parse.contains("сегодня")) {
            parse = parse.replace("сегодня", LocalDateTime.now().format(dateFormatter));
        } else if (parse.contains("вчера")) {
            parse = parse.replace("вчера", LocalDateTime.now().minusDays(1).format(dateFormatter));
        } else {
            String month = parse.split(" ")[1];
            parse = parse.replace(month, MONTHS.get(month));
        }
        return LocalDateTime.parse(parse, dateTimeFormatter);
    }
}