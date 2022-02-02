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
    public List<Post> list(String link) {
        List<Post> list = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            Document doc = null;
            try {
                doc = Jsoup.connect(link + i).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements row = doc.select(".postslisttopic");
            for (Element element : row) {
                Element child = element.child(0);
                String postLink = child.attr("href");
                Post post = detail(postLink);
                if (post.getTitle().toLowerCase().contains("java")
                        && !post.getTitle().toLowerCase().contains("javascript")) {
                    list.add(post);
                }
            }
        }
        return list;
    }

    @Override
    public Post detail(String link) {
        Document page = null;
        try {
            page = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String title = page.select(".messageHeader").get(0).text();
        String description = page.select(".msgBody").get(1).text();
        String dateTime = page.select(".msgFooter").text();
        dateTime = dateTime.substring(0, dateTime.indexOf(":") + 3);
        LocalDateTime created = dateTimeParser.parse(dateTime);
        return new Post(title, link, description, created);
    }
}