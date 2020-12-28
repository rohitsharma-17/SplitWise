package com.hashedin.services;

import com.hashedin.entities.Bills;
import com.hashedin.entities.Groups;
import com.hashedin.entities.Users;
import com.hashedin.exceptions.BadRequestException;
import com.hashedin.exceptions.ResourceNotFoundException;
import com.hashedin.models.requests.GroupRequest;
import com.hashedin.models.responses.GroupResponse;
import com.hashedin.repositories.GroupRepository;
import com.hashedin.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IGroupServiceTest {

    @Mock
    private GroupRepository groupRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private IBillsService billsService;
    @InjectMocks
    private GroupsServiceImpl groupsService;
    private Groups groups;
    private GroupRequest groupRequest;
    private GroupResponse groupResponse;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        groups = new Groups();
        Users user1 = new Users();
        user1.setEmail("rosh@gmail.com");
        user1.setId(1l);
        user1.setName("Rosh1");
        Users user2 = new Users();
        user2.setEmail("rosh@hashedin.com");
        user2.setId(2l);
        user2.setName("Rosh2");
        groups.setId(1l);
        groups.setGroupName("abc");
//        user1.setGroupsList(Arrays.asList(groups));
//        user2.setGroupsList(Arrays.asList(groups));
        groups.setMembers(Arrays.asList(user1,user2));
        groupRequest = new GroupRequest();
        groupRequest.setGroupName("abc");
//        user1.setGroupsList(Arrays.asList(groups));
//        user2.setGroupsList(Arrays.asList(groups));
        groupRequest.setMembers(Arrays.asList(user1,user2));
        groupResponse = new GroupResponse();
        BeanUtils.copyProperties(groups,groupResponse);
    }

    @Test(expected = BadRequestException.class)
    public void createGroupWithRequestNullTest() throws Exception{
        groupsService.createGroup(null);
    }

    @Test
    public void createGroupTest() throws Exception{
        Mockito.when(groupRepository.save(Mockito.any())).thenReturn(groups);
        Assert.assertEquals(groupResponse,groupsService.createGroup(groupRequest));
    }

    @Test
    public void getGroupTest() throws Exception{
        Mockito.when(groupRepository.findByIdAndDeletedFalse(Mockito.anyLong())).thenReturn(groups);
        Assert.assertEquals(groups,groupsService.getGroup(1l));
    }

    @Test(expected = BadRequestException.class)
    public void getGroupWithIdNullTest() throws Exception{
        groupsService.getGroup(null);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getGroupWithNotFoundNullTest() throws Exception{
        groupsService.getGroup(1l);
    }

    @Test
    public void addMemberInGroupTest() throws Exception{
        Users users = new Users();
        users.setEmail("rosh1@gmail.com");
        users.setId(3l);
        users.setName("Rosh3");
        groups.getMembers().add(users);
        Mockito.when(groupRepository.findByIdAndDeletedFalse(Mockito.anyLong())).thenReturn(groups);
        Mockito.when(userRepository.findByEmailAndDeletedFalse(Mockito.any())).thenReturn(users);
        Mockito.when(groupRepository.save(Mockito.any())).thenReturn(groups);
        Assert.assertEquals("User Added",groupsService.addMemberInGroup("rosh1@gmail.com",1l));
    }

    @Test
    public void getSimplifiedDebtTest() throws Exception{
        Map<String,Double> map = new HashMap<>();
        map.put("rosh@gmail.com",new Double(-50));
        map.put("rosh@hashedin.com",new Double(50));
        Bills bills = new Bills();
        bills.setId(1l);
        bills.setGroupId(1l);
        bills.setPaidBy("rosh@gmail.com");
        bills.setAmount(new Double(100));
        bills.setAmountPerHead(map);
        Mockito.when(billsService.getListOfBill(Mockito.anyLong())).thenReturn(Arrays.asList(bills));
        Assert.assertNotNull(groupsService.getSimplifiedDebt(1l));
    }

}
