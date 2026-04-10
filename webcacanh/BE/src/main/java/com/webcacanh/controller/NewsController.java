package com.webcacanh.controller;

import com.webcacanh.entity.News;
import com.webcacanh.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public String newsPage(Model model) {

        List<News> newsList = newsService.getAllNews();

        if(newsList == null){
            System.out.println("NEWS LIST NULL");
        }else{
            System.out.println("TOTAL NEWS: " + newsList.size());
        }

        model.addAttribute("listNews", newsList);

        return "news";
    }

    @GetMapping("/testdb")
public String testDB(Model model) {

    model.addAttribute("listNews", newsService.getAllNews());

    return "news";
}
}