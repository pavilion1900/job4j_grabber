package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.*;

import java.io.IOException;
import java.time.LocalDateTime;

public class SqlRuParse {
    public Post getPost(String link) throws IOException {
        DateTimeParser parser = new SqlRuDateTimeParser();
        Document page = Jsoup.connect(link).get();
        String title = page.select(".messageHeader").get(0).text();
        String description = page.select(".msgBody").get(1).text();
        String dateTime = page.select(".msgFooter").text();
        dateTime = dateTime.substring(0, dateTime.indexOf(":") + 3);
        LocalDateTime created = parser.parse(dateTime);
        return new Post(title, link, description, created);
    }

    public static void main(String[] args) throws IOException {
        SqlRuParse sqlRuParse = new SqlRuParse();
        for (int i = 1; i < 2; i++) {
            String url = "https://www.sql.ru/forum/job-offers/";
            Document doc = Jsoup.connect(url + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element element : row) {
                Element child = element.child(0);
                String link = child.attr("href");
                Post post = sqlRuParse.getPost(link);
                System.out.println(post);
            }
        }
    }
}