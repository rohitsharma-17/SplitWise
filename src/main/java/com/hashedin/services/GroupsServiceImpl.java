package com.hashedin.services;

import com.google.common.base.Strings;
import com.hashedin.entities.Groups;
import com.hashedin.entities.Users;
import com.hashedin.exceptions.BadRequestException;
import com.hashedin.exceptions.ResourceNotFoundException;
import com.hashedin.models.requests.GroupRequest;
import com.hashedin.models.responses.GroupResponse;
import com.hashedin.repositories.GroupRepository;
import com.hashedin.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class GroupsServiceImpl implements IGroupsService{

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IBillsService billsService;

    @Override
    public GroupResponse createGroup(GroupRequest groupRequest) throws Exception {
        if(groupRequest == null){
            log.error("Request is null");
            throw new BadRequestException("Request is null");
        }
        Groups groups = new Groups();
        groups.setGroupName(groupRequest.getGroupName());
        List<Users> usersList = new ArrayList<>();
        for (Users users:groupRequest.getMembers()) {
            usersList.add(users);
        }
        groups.setMembers(usersList);
        GroupResponse groupResponse = new GroupResponse();
        groups = groupRepository.save(groups);
        BeanUtils.copyProperties(groups,groupResponse);
        if( null != groups.getMembers() && groups.getMembers().size()> 0){
            Groups finalGroups = groups;
            groups.getMembers().stream().forEach(users -> {
                users = userRepository.findByEmailAndDeletedFalse(users.getEmail());
                if(null != users.getGroupsList()){
                    users.getGroupsList().add(finalGroups);
                }else {
                    users.setGroupsList(Arrays.asList(finalGroups));
                }
                userRepository.save(users);
            });
        return groupResponse;
    }

    @Override
    public Groups getGroup(Long groupId) throws Exception {
        if(null == groupId){
            log.error("GroupId is null");
            throw new BadRequestException("GroupId is null");
        }
        Groups groups = groupRepository.findByIdAndDeletedFalse(groupId);
        if(null == groups){
            log.error("GroupId not found");
            throw new ResourceNotFoundException("GroupId not found");
        }
        return groups;
    }

    @Override
    public String addMemberInGroup(String email, Long groupId) throws Exception {
        if(Strings.isNullOrEmpty(email) || null == groupId){
            log.error("GroupId or email is null");
            throw new BadRequestException("Request is null");
        }
        Users users = userRepository.findByEmailAndDeletedFalse(email);
        if(null == users){
            log.error("User doesn't exist");
            throw new ResourceNotFoundException("User doesn't exist");
        }
        Groups groups = groupRepository.findByIdAndDeletedFalse(groupId);
        if(null == groups){
            log.error("GroupId not found");
            throw new ResourceNotFoundException("Group not found");
        }
        groups.getMembers().add(users);
        groupRepository.save(groups);
        return "User Added";
    }

    @Override
    public Map<String, Double> getSimplifiedDebt(Long groupId) throws Exception {
        if(null == groupId){
            log.error("GroupId is null");
            throw new BadRequestException("GroupId is null");
        }
        Map<String,Double> detail = new HashMap<>();
        billsService.getListOfBill(groupId).forEach(
                bills -> {
                    bills.getAmountPerHead().forEach((k,v) ->{
                        if(null != k){
                            Double tempValue = detail.get(k);
                            detail.put(k,tempValue + v);
                        }else {
                            detail.put(k, v);
                        }
                    });
                }
        );
        return detail;
    }

    @Override
    public List<Groups> getAll() throws Exception {
        return groupRepository.findAll();
    }
}
