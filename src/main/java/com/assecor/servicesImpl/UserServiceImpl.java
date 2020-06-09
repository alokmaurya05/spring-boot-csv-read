package com.assecor.servicesImpl;

import com.assecor.entity.Color;
import com.assecor.entity.User;
import com.assecor.repository.UserRepository;
import com.assecor.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public List<User> getAllUsers() {
		logger.info("Finding All Users");
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getUserById(Long id) {
		logger.info("Find user by id "+id);
		return userRepository.findById(id);
	}

	@Override
	public List<User> getUsersWithColor(String color) {
		List<User> userList = new ArrayList<User>();
		try {
			logger.info("User request with color "+ color);
			userList = userRepository.findByColorMappingColor(color);
		} catch (NoSuchElementException ex) {
			logger.error("Not found color {}", color);
			throw new NoSuchElementException("Color " +color +" not found");
		}
		return userList;

	}

	@Override
	public User save(User user) {
		logger.info("User save service ");
		return userRepository.save(user);
	}
	@Override
	public User update(User user,long id) {
		logger.info("User update service ");
		    User newUser = new User();
			newUser.setId(id);
			newUser.setName(user.getName());
			newUser.setLastName(user.getLastName());
			newUser.setZipCode(user.getZipCode());
			newUser.setCity(user.getCity());
			newUser.setColorMapping(new Color(user.getColorMapping().getColor_id()));
   
		return userRepository.save(newUser);
	}
	
	@Override
	public void deleteById(Long id) {
		logger.info("User delete by id {} "+id);
		userRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void deleteAll(List<User> users) {
		logger.info("All user delete service ");
		userRepository.deleteAll();
	}
	
}
