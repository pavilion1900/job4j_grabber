package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import ru.job4j.grabber.*;
import ru.job4j.grabber.utils.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class SqlRuParse implements Parse {
    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> list = new ArrayList<>();
        SqlRuParse sqlRuParse = new SqlRuParse(dateTimeParser);
        for (int i = 1; i < 6; i++) {
            Document doc = Jsoup.connect(link + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element element : row) {
                Element child = element.child(0);
                String postLink = child.attr("href");
                Post post = sqlRuParse.detail(postLink);
                list.add(post);
            }
        }
        return list;
    }

    @Override
    public Post detail(String link) throws IOException {
        Document page = Jsoup.connect(link).get();
        String title = page.select(".messageHeader").get(0).text();
        String description = page.select(".msgBody").get(1).text();
        String dateTime = page.select(".msgFooter").text();
        dateTime = dateTime.substring(0, dateTime.indexOf(":") + 3);
        LocalDateTime created = dateTimeParser.parse(dateTime);
        return new Post(title, link, description, created);
    }
}