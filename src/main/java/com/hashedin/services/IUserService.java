package com.hashedin.services;

import com.hashedin.models.requests.UserRequest;
import com.hashedin.models.responses.UserResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IUserService {

    UserResponse addUser(UserRequest userRequest) throws Exception;

    UserResponse updateUser(String email, UserRequest userRequest) throws Exception;

    UserResponse getUser(String email) throws Exception;

    String deleteUser(String email) throws Exception;

    Map<String,Double> detailedDueOfUser(String email) throws Exception;
}
