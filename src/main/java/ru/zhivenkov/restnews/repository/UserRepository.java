package ru.zhivenkov.restnews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zhivenkov.restnews.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
