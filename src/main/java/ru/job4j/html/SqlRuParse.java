package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlRuParse {
    public static void main(String[] args) throws IOException {
        for (int i = 1; i < 6; i++) {
            String url = "https://www.sql.ru/forum/job-offers/";
            Document doc = Jsoup.connect(url + i).get();
            Elements row = doc.select(".postslisttopic");
            for (Element element : row) {
                Element child = element.child(0);
                System.out.println(child.attr("href"));
                System.out.println(child.text());
                Element parent = element.parent();
                System.out.println(parent.child(5).text());
            }
        }
    }
}
