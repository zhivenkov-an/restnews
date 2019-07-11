package ru.zhivenkov.restnews.serivce;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhivenkov.restnews.entity.News;
import ru.zhivenkov.restnews.repository.NewsRepository;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void createNews(News news) {
        newsRepository.save(news);
    }

    public List<News> findAll(){
        return newsRepository.findAll();
    }

    public News findById(Long newsId){
        return newsRepository.findById(newsId).orElse(null);
    }



}
