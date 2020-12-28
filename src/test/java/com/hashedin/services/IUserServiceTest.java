package com.hashedin.services;

import com.hashedin.entities.Users;
import com.hashedin.models.requests.UserRequest;
import com.hashedin.models.responses.UserResponse;
import com.hashedin.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class IUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private IBillsService billsService;
    @InjectMocks
    private UserServiceImpl userService;
    private UserRequest userRequest;
    private Users users;
    private UserResponse userResponse;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        users = new Users();
        users.setName("rosh");
        users.setId(1l);
        users.setEmail("rosh@gmail.com");
        userRequest = new UserRequest();
        userRequest.setName("rosh");
        userRequest.setEmail("rosh@gmail.com");
        userResponse = new UserResponse();
        BeanUtils.copyProperties(users,userResponse);
    }


    @Test
    public void addUser() throws Exception{
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(users);
        Assert.assertEquals(userResponse,userService.addUser(userRequest));
    }

    @Test
    public void updateUser() throws Exception{
        Mockito.when(userRepository.findByEmailAndDeletedFalse(Mockito.any())).thenReturn(users);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(users);
        Assert.assertEquals(userResponse,userService.updateUser("rosh@gmail.com",userRequest));
    }

    @Test
    public void getUser() throws Exception{
        Mockito.when(userRepository.findByEmailAndDeletedFalse(Mockito.any())).thenReturn(users);
        Assert.assertEquals(userResponse,userService.getUser("rosh@gmail.com"));
    }

    @Test
    public void deleteUser() throws Exception{
        Mockito.when(userRepository.findByEmailAndDeletedFalse(Mockito.any())).thenReturn(users);
        Assert.assertEquals("deleted success",userService.deleteUser("rosh@gmail.com"));
    }


}
