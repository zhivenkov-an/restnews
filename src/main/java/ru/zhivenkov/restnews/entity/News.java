package ru.zhivenkov.restnews.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "news")
public class News{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    public News() {// jpa only

    }

    public News(String title, String content, Date creation_date, @NotNull User creator_id) {
        this.title = title;
        this.content = content;
        this.creation_date = creation_date;
        this.creator_id = creator_id;
    }


    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "creation_date")
    private Date creation_date;

    @NotNull
    @JoinColumn(name = "creator_id")
    // аналог реаляционного внешнего ключа - foreign key. targetEntity - класс сущности. fetch как должны загружаться данные ассоциации: отложено LAZY или немедленно EAGER. Cascade Операции, которые должны быть каскадированы в цель ассоциации
    @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private User creator_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public User getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(User creator_id) {
        this.creator_id = creator_id;
    }


    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", creation_date=" + creation_date +
                ", creator_id=" + creator_id.toString() +
                '}';
    }

}
