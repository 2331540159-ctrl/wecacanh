package com.webcacanh.service;

import com.webcacanh.entity.News;
import com.webcacanh.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

}   