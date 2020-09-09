package com.example.lecture.controller;

import com.example.lecture.entity.NaverNews;
import com.example.lecture.entity.News;
import com.example.lecture.service.NewsCrawlService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log
@RestController
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class CrawlController {
    @Autowired
    NewsCrawlService newsCrawler;

    @GetMapping("{category}")
    public List<News> crawling(@PathVariable String category) {
        log.info("crawling(): " + category);

        newsCrawler.mainCrawler(category);
        return newsCrawler.newsFindAll();
    }

    @GetMapping("naver")
    public List<NaverNews> naverCrawling() {
        log.info("naverCrawling(): ");

        newsCrawler.naverMainCrawler();
        return newsCrawler.naverNewsFindAll();
    }
}