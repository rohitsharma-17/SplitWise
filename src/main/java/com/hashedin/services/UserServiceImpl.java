package com.hashedin.services;

import com.google.common.base.Strings;
import com.hashedin.entities.Users;
import com.hashedin.exceptions.ResourceNotFoundException;
import com.hashedin.models.requests.UserRequest;
import com.hashedin.models.responses.UserResponse;
import com.hashedin.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IBillsService billsService;

    @Override
    public UserResponse addUser(UserRequest userRequest) throws Exception {
        UserResponse userResponse = new UserResponse();
        if(null == userRequest){
            userResponse.setMessage("Bad Request");
            userResponse.setStatusCode(400);
            return userResponse;
        }
        Users users = new Users();
        BeanUtils.copyProperties(userRequest, users);
        BeanUtils.copyProperties(userRepository.save(users),userResponse);
        return userResponse;
    }

    @Override
    public UserResponse updateUser(String email, UserRequest userRequest) throws Exception {
        UserResponse userResponse = new UserResponse();
        if(null == userRequest){
            userResponse.setMessage("Bad Request");
            userResponse.setStatusCode(400);
            return userResponse;
        }
        Users users = userRepository.findByEmailAndDeletedFalse(email);
        if(null == users){
            userResponse.setMessage("Resource not found");
            userResponse.setStatusCode(404);
            return userResponse;
        }
        BeanUtils.copyProperties(userRequest, users);
        BeanUtils.copyProperties(userRepository.save(users),userResponse);
        return userResponse;
    }

    @Override
    public UserResponse getUser(String email) throws Exception {
        UserResponse userResponse = new UserResponse();
        if(Strings.isNullOrEmpty(email)){
            log.error("Email is null or empty");
            userResponse.setMessage("Bad Request");
            userResponse.setStatusCode(400);
            return userResponse;
        }
        Users users = userRepository.findByEmailAndDeletedFalse(email);
        if(null == users){
            log.error("user not found");
            userResponse.setStatusCode(404);
            userResponse.setMessage("user not found");
            return userResponse;
        }

        BeanUtils.copyProperties(users,userResponse);
        return userResponse;
    }

    @Override
    public String deleteUser(String email) throws Exception {
        if(Strings.isNullOrEmpty(email)){
            log.error("Email is null or empty");
        }
        Users users = userRepository.findByEmailAndDeletedFalse(email);
        if(null == users){
            log.error("user not found");
        }
        users.setDeleted(true);
        userRepository.save(users);
        return "deleted success";
    }

    @Override
    public Map<String,Double> detailedDueOfUser(String email)  throws Exception{
        Map<String,Double> detail = new HashMap<>();
        //Not paid by you
        Users user = userRepository.findByEmailAndDeletedFalse(email);
        if(null == user){
            throw new ResourceNotFoundException("User not found");
        }
        user.getGroupsList().forEach(
                group -> {
                    try {
                        billsService.getListOfBill(group.getId()).stream().filter(bills ->
                                !bills.getPaidBy().equalsIgnoreCase(user.getEmail())
                        ).collect(Collectors.toList()).forEach(bills -> {
                            Double amount = bills.getAmountPerHead().get(user.getEmail());
                            if(null != detail.get(user.getEmail())){
                                Double tempAmount = detail.get(user.getEmail());
                                detail.put(bills.getPaidBy(),(amount+tempAmount));
                            }else {
                                detail.put(bills.getPaidBy(),amount);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        //if bill is paid by you then remaining people
        user.getGroupsList().forEach(group -> {
            try {
                billsService.getListOfBill(group.getId()).stream().filter(bills ->
                        bills.getPaidBy().equalsIgnoreCase(user.getEmail())
                ).collect(Collectors.toList()).forEach(bills -> {
                    bills.getAmountPerHead().forEach((k,v) ->{
                        Double amount = bills.getAmountPerHead().get(user.getEmail());
                        if(null != detail.get(user.getEmail())){
                            Double tempAmount = detail.get("");
                            detail.put(user.getEmail(), amount*(-1) + tempAmount);
                        }else {
                            detail.put(user.getEmail(), amount*(-1));
                        }
                    });
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return detail;
    }
}
