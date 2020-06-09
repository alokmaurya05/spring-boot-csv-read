package com.assecor.services;


import java.util.List;
import java.util.Optional;

import com.assecor.entity.User;

public interface UserService
{
    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    List<User> getUsersWithColor(String color);

    User save(User user);
    
    User update(User user,long id);

    void deleteById(Long id);

    void deleteAll(List<User> users);
}
