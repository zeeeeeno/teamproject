package com.example.lecture.service;

import com.example.lecture.entity.NaverNews;
import com.example.lecture.entity.News;

import com.example.lecture.repository.NaverNewsRepository;
import com.example.lecture.repository.NewsRepository;

import lombok.extern.java.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Lazy
@Log
public class NewsCrawlService {
    @Autowired
    NewsRepository newsRepository;

    @Autowired
    NaverNewsRepository naverNewsRepository;

    private Document document;

    public Document connectUrl(String url) {
        log.info("connectUrl(): " + url);

        try {
            Connection.Response homepage = Jsoup.connect(url).method(Connection.Method.GET)
                    .userAgent("Mozila/5.0 (X11; Linux x86_64; rv:10.0) Gecko/20100101 " +
                            "Firefox/10.0 AppleWebKit/537.36 (KHTML, like Gecko) " +
                            "Chrome/51.0.2704.103 Safari/537.36")
                    .execute();

            document = homepage.parse();
        } catch (Exception e) {
            System.out.println("Error in mainCrawler");
        }

        return document;
    }

    public List<News> newsFindAll() {
        log.info("newsFindAll()");

        return newsRepository.findAll();
    }

    public List<NaverNews> naverNewsFindAll() {
        log.info("naverNewsFindAll()");

        return naverNewsRepository.findAll();
    }

    public void mainCrawler(String category) {
        log.info("mainCrawler(): " + category);

        document = connectUrl("https://news.daum.net/" + category);
        newsRepository.deleteAll();

        // 양호
        daumNews(document.select("ul.list_mainnews>li>div.cont_thumb>strong.tit_thumb>a"), category);
        daumNews(document.select("div.item_mainnews>div.cont_thumb>strong.tit_thumb>a"), category);
        daumNews(document.select("strong.tit_mainnews>a"), category);
        daumNews(document.select("ul.list_mainnews>li>a>img"), category);
        daumNews(document.select("ul.list_issue>li>span.wrap_thumb>img"), category);
        daumNews(document.select("ul.list_mainnews2>li>strong.tit_mainnews>a"), category);
        daumNews(document.select("div.item_issue>a>img"), category);
        daumNews(document.select("div.item_mainnews>a>img"), category);
    }

    public void naverMainCrawler () {
        log.info("naverMainCrawler()");

        document = connectUrl("https://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=105");
        naverNewsRepository.deleteAll();

        // 좋음
        naverNews(document.select("div.cluster_body>ul.cluster_list>li.cluster_item" +
                ">div.cluster_text>a"));
        naverNews(document.select("div.cluster_head>div.cluster_head_inner" +
                ">div.cluster_head_topic_wrap>h2.cluster_head_topic>a"));
        naverNews(document.select("div.cluster_body>ul.cluster_list>li.cluster_item>div.cluster_thumb" +
                ">div.cluster_thumb_inner>a>img"));
        naverNews(document.select("div.section_body>ul.type06_headline>li>dl>dt.photo>a>img"));
    }

    public void daumNews(Elements elements, String category) {
        log.info("daumNews(): elements - " + elements + ", category - " + category);
        News news = null;

        for (int i = 0; i < elements.size(); i++) {
            news = new News();

            news.setNewsNo(String.valueOf(newsRepository.findAll().size() + 1));
            news.setAddress(elements.get(i).attr("href"));
            news.setCategory("digital");
            news.setTitle(elements.get(i).text());
            news.setImage(elements.get(i).attr("src"));

            newsRepository.save(news);
        }
    }

    public void naverNews(Elements elements) {
        log.info("naverNews() elements - " + elements);

        NaverNews naverNews = null;

        for (int i = 0; i < elements.size(); i++) {
            naverNews = new NaverNews();

            naverNews.setNewsNo(String.valueOf(naverNewsRepository.findAll().size() + 1));
            naverNews.setAddress(elements.get(i).attr("href"));
            naverNews.setTitle(elements.get(i).text());
            naverNews.setImage(elements.get(i).attr("src"));

            naverNewsRepository.save(naverNews);
        }
    }
}