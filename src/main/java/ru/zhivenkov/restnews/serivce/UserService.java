package ru.zhivenkov.restnews.serivce;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhivenkov.restnews.entity.User;
import ru.zhivenkov.restnews.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUsers(User user) {
        userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long userId){
        return userRepository.findById(userId).orElse(null);
    }


}
