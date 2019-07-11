package ru.zhivenkov.restnews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zhivenkov.restnews.entity.News;

public interface NewsRepository extends JpaRepository<News,Long> {

}
