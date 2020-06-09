package com.assecor.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.assecor.entity.Color;
import com.assecor.entity.User;
import com.assecor.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource ("classpath:config.properties")
public class DBUtil implements ApplicationRunner
{
    @Autowired
    private UserRepository userRepository;

    @Value ("${csvFile}")
    private String csvFile;

    private static final String DELIMITER_SPACE = "\\s+";

    public void run(ApplicationArguments args)
    {
        insertData();
    }

    private void insertData()
    {
        try
        {
            List<List<String>> records = CSVUtil.readFile(csvFile);
            createUsers(records);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void createUsers(List<List<String>> userRecords)
    {
        List<User> users = new ArrayList<>();
        for (List<String> userRecord : userRecords)
        {
            users.add(getUser(userRecord));
        }

        userRepository.saveAll(users);
    }

    private User getUser(List<String> userRecord)
    {
        User user = new User();
        user.setLastName(userRecord.get(0));
        user.setName(userRecord.get(1));
        setZipCodeAndCity(userRecord.get(2), user);
        user.setColorMapping(getColor(userRecord.get(3)));
        return user;
    }

    private void setZipCodeAndCity(String zipAndCity, User user)
    {
        String[] zipAndCityArr = zipAndCity.trim().split(DELIMITER_SPACE,2);
        user.setZipCode(Integer.valueOf(zipAndCityArr[0]));
        user.setCity(zipAndCityArr[1]);
    }

    private Color getColor(String color_id)
    {
        Color color = new Color();
        color.setColor_id(Integer.valueOf(color_id));
     
        return color;
    }
}
