package com.assecor.resource;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import com.assecor.application.AssecorApplication;
import com.assecor.entity.User;

import com.assecor.resources.UserResource;

import com.assecor.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;


/*This class for resource test
 * We can add more test case also. This is just a basic.
 * */
@SpringBootTest(classes={AssecorApplication.class})
@RunWith(SpringRunner.class)
public class UserResourceTest 
{
	private final String URL = "/api/v1/persons";
	MockMvc mockMvc;
	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	UserResource userResource;
    
	@MockBean
	UserService userService;
	
	@Before
	public void setup()
	{
		 this.mockMvc = standaloneSetup(this.userResource).build();// Standalone context
	}
	
	@Test
	public void testSave() throws Exception 
	{      
		   User user = new User(new Long(101),"Harry","Jon",110051,"Berlin",null);
		   when(userService.save(any(User.class))).thenReturn(user);
		
			mockMvc.perform(post(URL).content(mapper.writeValueAsBytes(user)).contentType(MediaType.APPLICATION_JSON))
			 .andExpect(status().isCreated())
			 .andExpect(jsonPath("$.id", is(101)))
			 .andExpect(jsonPath("$.name", is("Harry")))
			 .andExpect(jsonPath("$.lastName", is("Jon")))
			 .andExpect(jsonPath("$.zipCode", is(110051)))
			 .andExpect(jsonPath("$.city", is("Berlin"))) ;
	} 
	
	@Test
	public void testGetAllUser() throws Exception 
	{
		   when(userService.getAllUsers()).thenReturn(Collections.singletonList(getUser()));
		
			mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON))
			 .andExpect(status().isOk())
			 .andExpect(jsonPath("$.[0].id", is(200)))
			 .andExpect(jsonPath("$.[0].name", is("Harry")))
			 .andExpect(jsonPath("$.[0].lastName", is("Jon")))
			 .andExpect(jsonPath("$.[0].zipCode", is(110051)))
			 .andExpect(jsonPath("$.[0].city", is("Berlin")))
			 .andExpect(jsonPath("$.[0].color", is("grun"))) ;
	} 
	
	@Test
	public void testGetUserById() throws Exception 
	{
		   when(userService.getUserById(Mockito.anyLong())).thenReturn(Optional.of(getUser()));
		
			mockMvc.perform(get(URL+"/200").content(mapper.writeValueAsBytes(new Long(200))).contentType(MediaType.APPLICATION_JSON))
			 .andExpect(status().isOk())
			 .andExpect(jsonPath("$.id", is(200)))
			 .andExpect(jsonPath("$.name", is("Harry")))
			 .andExpect(jsonPath("$.lastName", is("Jon")))
			 .andExpect(jsonPath("$.zipCode", is(110051)))
			 .andExpect(jsonPath("$.city", is("Berlin")))
			 .andExpect(jsonPath("$.color", is("grun"))) ;
	} 
	
	@Test
	public void testGetUserByColor() throws Exception 
	{
		   when(userService.getUsersWithColor(Mockito.anyString()))
		                    .thenReturn(Collections.singletonList(getUser()));
		
			mockMvc.perform(get(URL+"/color/grun").contentType(MediaType.APPLICATION_JSON))
			 .andExpect(status().isOk())
			 .andExpect(jsonPath("$.[0].id", is(200)))
			 .andExpect(jsonPath("$.[0].name", is("Harry")))
			 .andExpect(jsonPath("$.[0].lastName", is("Jon")))
			 .andExpect(jsonPath("$.[0].zipCode", is(110051)))
			 .andExpect(jsonPath("$.[0].city", is("Berlin")))
			 .andExpect(jsonPath("$.[0].color", is("grun")));
	} 
	
	@Test
	public void testUpdateUser() throws Exception 
	{
		   User usertoUpdate = new User(new Long(101),"Harry","Jon",110051,"Berlin","grun"); 
		   User updatedUser = new User(new Long(101),"Alex","Kumar",110551,"Koln",null);
		   when(userService.getUserById(Mockito.anyLong())).thenReturn(Optional.of(usertoUpdate));
		   when(userService.save(any(User.class))).thenReturn(updatedUser);
		
			mockMvc.perform(put(URL+"/101").content(mapper.writeValueAsBytes(usertoUpdate)).contentType(MediaType.APPLICATION_JSON))
			 .andExpect(status().isOk());
	} 
	
	@Test
	public void testdeleteUserById() throws Exception 
	{
		 when(userService.getUserById(Mockito.anyLong())).thenReturn(Optional.of(getUser()));    
		doNothing().when(userService).deleteById(Mockito.anyLong());
		    mockMvc.perform(delete(URL+"/8").contentType(MediaType.APPLICATION_JSON))
			 .andExpect(status().isOk());
	} 
			
    @Test
    public void testdeleteAllUser() throws Exception 
			{ 
    	      User user = new User(new Long(101),"Harry","Jon",110051,"Berlin","grun");
    	      doNothing().when(userService).deleteAll(Mockito.anyList());
		      mockMvc.perform(delete(URL).content(mapper.writeValueAsBytes(Collections.singletonList(user))).contentType(MediaType.APPLICATION_JSON))
			 .andExpect(status().isOk());
				
			} 
	
	private User getUser()
	{
		return new User(new Long(200),"Harry","Jon",110051,"Berlin","grun");
		
	}
}
