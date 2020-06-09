package com.assecor.resources;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.assecor.buildResponse.Response;
import com.assecor.entity.User;
import com.assecor.error.InvalidUserException;
import com.assecor.error.NoDataFoundException;
import com.assecor.services.UserService;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import static com.assecor.buildResponse.MsgConstants.*;


@RestController
@RequestMapping (value = "/api/v1/persons")
public class UserResource
{
	public static final Logger logger = LoggerFactory.getLogger(UserResource.class);
   
   
	@Autowired
    private UserService userService;

 // -------------------Retrieve All Users---------------------------------------------

	 /**
	 * This method give you all the users
	 * @return ResponseEntity<List<User>>
	 */
    @GetMapping
    public ResponseEntity<List<User>> getAllUser()
    {
    	logger.info("Fetching All Users");
    	List<User> users = userService.getAllUsers();
		if (users.isEmpty()) 
		{
	     throw new NoDataFoundException("Not Found");
		}
		return Response.success(users, HttpStatus.OK);
    }
 // -------------------Retrieve Single User----------------------------------------------
	 /**
	 * This method gives user based on user id 
	 * @param id  Long
	 * @return ResponseEntity<User>
	 */
    @GetMapping (value = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable ("id") Long id)
    {
    	logger.info("Fetching User with id {}", id);
		
		if (! userService.getUserById(id).isPresent())
		{
			logger.error("User with id {} not found.", id);
			throw new InvalidUserException(id);
		}
		return Response.success(userService.getUserById(id).get(), HttpStatus.OK);
    }
  // -------------------Retrieve Users with color------------------------------------------
    /**
	 * This method gives user based on color 
	 * @param color  String
	 * @return ResponseEntity<List<User>>
	 */
    @GetMapping (value = "color/{color}")
    public ResponseEntity<List<User>> getUserByColor(@PathVariable ("color") String color)
    {
    	logger.info("Fetching User with color", color);
    	List<User> users = userService.getUsersWithColor(color);
    	if (users.isEmpty())
		{
			logger.error("User with color {} not found.", color);
			throw new NoDataFoundException("Color "+color +" not found" );
		}
        return Response.success(users, HttpStatus.OK);
    }
  // -------------------Save User ---------------------------------------------------------
    /**
	 * This method use to save user 
	 * @param user  User
	 * @return ResponseEntity<User>
	 */
    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user )
    {
    	logger.info("User creation......");
        user = userService.save(user);
        return Response.success(user, HttpStatus.CREATED);
    } 
    // -------------------Update User -------------------------------------------------------
    /**
	 * This method use to update user based on user id
	 * @param id   Long
	 * @param user User
	 * @return ResponseEntity<User>
	 */
    @PutMapping (value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id,@Valid @RequestBody User user)
    {  
    	logger.info("User updation for id " +id);
    	
    	Optional<User> userExist=userService.getUserById(id);
    	
        if (! userExist.isPresent()) 
		{
        logger.error("User id {} not found.", id);	
	     throw new InvalidUserException(id);
		}
        return Response.success(userService.update(user,id),HttpStatus.OK);
    }
 // -------------------Delete User by ID -----------------------------------------------
    /**
	 * This method use to delete user based on user id
	 * @param id   Long
	 * @return ResponseEntity<String>
	 */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String>  deleteUserById(@PathVariable ("id") Long id)
    {
    	if (!userService.getUserById(id).isPresent()) {
            logger.error("User id {} not found." + id );
            throw new InvalidUserException(id);
        }
    	logger.info("User deletion for id {}", id);
    	userService.deleteById(id);
        return Response.success(ENTITY_DELETED, HttpStatus.OK);
    }
 // -------------------Delete All the users ----------------------------------------------
    /**
	 * This method use to delete all the users
	 * @param users   List<User>
	 * @return ResponseEntity<String>
	 */
    @DeleteMapping
    public ResponseEntity<String> deleteAllUser(@RequestBody List<User> users)
    {
    	logger.info("Deleating All the user ");
    	userService.deleteAll(users);
        return Response.success(ENTITY_DELETED, HttpStatus.OK);
    }
   
}
