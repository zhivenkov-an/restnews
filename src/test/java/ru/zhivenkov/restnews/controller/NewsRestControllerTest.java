package ru.zhivenkov.restnews.controller;

import javafx.application.Application;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.zhivenkov.restnews.RestnewsApplication;
import ru.zhivenkov.restnews.entity.News;
import ru.zhivenkov.restnews.entity.User;
import ru.zhivenkov.restnews.repository.NewsRepository;
import ru.zhivenkov.restnews.repository.UserRepository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import static org.junit.Assert.*;

/**
 * @author zhivenkov-an
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RestnewsApplication.class)
@WebAppConfiguration
public class NewsRestControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String userName = "junit test user";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private User user;

    private List<News> newsList = new ArrayList<>();

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

       // this.newsRepository.deleteAllInBatch();
       // this.userRepository.deleteAllInBatch();

        this.user = userRepository.save(new User(userName));
        this.newsList.add(newsRepository.save(new News("test title", "test news", new Date(), this.user)));


    }



    @Test
    public void userNotFound() throws Exception {
        this.mockMvc.perform(post("/news/100500")
                .content(this.json(new News()))
                .contentType(contentType))
                .andExpect(status().isMethodNotAllowed());
    }


    @Test
    public void getById() throws Exception {
        this.mockMvc.perform(get("/news/1" ) )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("How much money earned M.Galkin"));
    }



    @Test
    public void getAll() throws Exception  {
        this.mockMvc.perform(get("/news" ) )
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("How much money earned M.Galkin"));
    }





    @Test
    public void add() {

        this.newsList.add(newsRepository.save(new News("test title2", "test news2", new Date(), this.user)));
    }

    @Test
    public void delById() {
        this.newsRepository.deleteById(this.newsList.get(0).getId());
       // this.newsRepository.deleteById(this.newsList.get(1).getId());
        this.userRepository.deleteById(this.user.getId());

    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}