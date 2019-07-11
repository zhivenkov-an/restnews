package ru.zhivenkov.restnews.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zhivenkov.restnews.entity.News;
import ru.zhivenkov.restnews.repository.NewsRepository;
import ru.zhivenkov.restnews.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/news")
public class NewsRestController {
    private final NewsRepository newsRepository;

    private final UserRepository userRepository;

    @Autowired
    public NewsRestController(NewsRepository newsRepository, UserRepository userRepository) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
    }


    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@RequestBody News input){


        this.validateUser(input.getCreator_id().getId()); // проверяем наличия автора

        News result = newsRepository.saveAndFlush(new News(input.getTitle(),input.getContent(),input.getCreation_date(),input.getCreator_id()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri());
        return new ResponseEntity<>("Добавлена новость " + result.getId(), httpHeaders, HttpStatus.CREATED);


    }



    @RequestMapping(value = "/{newsId}", method = RequestMethod.GET)
    Optional<News> getById(@PathVariable Long newsId) {

        return this.newsRepository.findById(newsId);
    }

    @RequestMapping(value = "/{newsId}", method = RequestMethod.DELETE)
    ResponseEntity<?> detById(@PathVariable Long newsId) {
        this.newsRepository.deleteById(newsId);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>("Новость удалена " + newsId, httpHeaders, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET)
    Collection<News> getAll() {
        return this.newsRepository.findAll();
    }

    private void validateUser(Long userId) {
        this.userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}
