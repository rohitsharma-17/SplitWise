package com.hashedin.services;

import com.hashedin.entities.Groups;
import com.hashedin.models.requests.GroupRequest;
import com.hashedin.models.responses.GroupResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IGroupsService {

    GroupResponse createGroup(GroupRequest groupRequest) throws Exception;

    Groups getGroup(Long groupId) throws Exception;

    String addMemberInGroup(String email,Long groupId) throws Exception;

    Map<String,Double> getSimplifiedDebt(Long groupId) throws Exception;

    List<Groups> getAll() throws Exception;

}
