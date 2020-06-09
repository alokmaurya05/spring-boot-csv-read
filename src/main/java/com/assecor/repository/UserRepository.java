package com.assecor.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assecor.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    List<User> findByColorMappingColor(String color);
}
