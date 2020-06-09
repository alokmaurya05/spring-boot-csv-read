package com.assecor.buildResponse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.assecor.entity.User;


/*This class use to Build response. */
public class Response
{
    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private String status = SUCCESS;
    private String message = null;
    private Object data = null;

    
    public static ResponseEntity<List<User>> success(List<User> users,HttpStatus status)
    {
        return new ResponseEntity<List<User>>(users,status);
    }
    public static ResponseEntity<User> success(User user,HttpStatus status)
    {
        return new ResponseEntity<User>(user,status);
    }
    public static ResponseEntity<?> success(Object data ,HttpStatus status)
    {
        return new ResponseEntity<Object>(data,status);
    }
    public static Response success(String message)
    {
        return new Response(SUCCESS, message);
    }
    public static ResponseEntity<String> success(String message, HttpStatus status)
    {
        return new ResponseEntity<String>(message, status);
    }
    public static Response success(String message, Object data)
    {
        return new Response(SUCCESS, message, data);
    }
    public static ResponseEntity<?> fail(Object data ,HttpStatus status)
    {
        return new ResponseEntity<Object>(data,status);
    }
    public static ResponseEntity<?> fail(HttpStatus status)
    {
        return new ResponseEntity<Object>(status);
    }
    public static Response fail(String message)
    {
        return new Response(FAIL, message);
    }

    private Response()
    {

    }

    private Response(String status, String message, Object data)
    {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private Response(String status, String message)
    {
        this(status, message, null);
    }

    public static String getSUCCESS()
    {
        return SUCCESS;
    }

    public static String getFAIL()
    {
        return FAIL;
    }

    public String getStatus()
    {
        return status;
    }

    public String getMessage()
    {
        return message;
    }

    public Object getData()
    {
        return data;
    }
}
