package com.assecor.servicesImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.assecor.entity.Color;
import com.assecor.entity.User;
import com.assecor.repository.UserRepository;

public class UserServiceImplTest {

	@Mock
	private UserRepository repository;
	@InjectMocks
	private UserServiceImpl userService;

	private User expectedUser = null;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		expectedUser = getExpectedUser();
	}

	@Test
	public void testGetAllUsers() {
		User user = new User(new Long(10), "Harry", "Jon", 110051, "Berlin", "grun");
		Mockito.when(repository.findAll()).thenReturn(Arrays.asList(user));

		assertEquals(Arrays.asList(expectedUser), userService.getAllUsers());
	}

	@Test
	public void testGetUserById() {
		Optional<User> user = Optional.of(new User(new Long(10), "Harry", "Jon", 110051, "Berlin", "grun"));
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(user);
		assertEquals(expectedUser, userService.getUserById(10L).get());

	}

	@Test
	public void testGetUsersWithColor() {
		User mockUser = new User(new Long(10), "Harry", "Jon", 110051, "Berlin", "grun");
		Mockito.when(repository.findByColorMappingColor(Mockito.anyString())).thenReturn(Arrays.asList(mockUser));
		assertEquals(Arrays.asList(expectedUser), userService.getUsersWithColor("grun"));

	}

	@Test(expected = NoSuchElementException.class)
	public void testColorNotFound() {
		doThrow(NoSuchElementException.class).when(repository).findByColorMappingColor(isA(String.class));
		userService.getUsersWithColor("Blue");
	}

	@Test
	public void testSaveUser() {
		Color color = new Color(1);
		User user = new User(new Long(10), "Harry", "Jon", 110051, "Berlin", null);
		user.setColorMapping(color);
		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
		User userExpected = new User(new Long(10), "Harry", "Jon", 110051, "Berlin", null);
		userExpected.setColorMapping(color);
		assertEquals(userExpected, userService.save(user));

	}

	@Test
	public void testUpdateUser() {
		Color color = new Color(1);
		User user = new User(new Long(10), "Harry", "Jon", null, "Berlin", null);
		user.setColorMapping(color);
		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
		User userExpected = new User(new Long(10), "Harry", "Jon", null, "Berlin", null);
		userExpected.setColorMapping(color);
		assertEquals(userExpected, userService.update(user, new Long(10)));

	}

	@Test
	public void testDeleteByUserId() {
		final Long userId = new Long(5l);
		userService.deleteById(userId);
		userService.deleteById(userId);
		verify(repository, times(2)).deleteById(userId);
	}

	@Test
	public void testDeleteAllUser() {
		User user = new User(new Long(11), "Harry", "Jon", 110051, "Berlin", "rot");
		userService.deleteAll(Collections.singletonList(user));
		verify(repository, times(1)).deleteAll();
	}

	private User getExpectedUser() {
		User expectedUser = new User();
		expectedUser.setId(10L);
		expectedUser.setName("Harry");
		expectedUser.setLastName("Jon");
		expectedUser.setZipCode(110051);
		expectedUser.setCity("Berlin");
		expectedUser.setColor("grun");
		return expectedUser;
	}
//We can add more test case here .
}
