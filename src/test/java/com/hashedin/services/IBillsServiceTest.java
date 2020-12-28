package com.hashedin.services;

import com.hashedin.entities.Bills;
import com.hashedin.entities.Groups;
import com.hashedin.entities.Users;
import com.hashedin.exceptions.BadRequestException;
import com.hashedin.exceptions.ResourceNotFoundException;
import com.hashedin.models.requests.BillRequest;
import com.hashedin.models.requests.SettlementRequest;
import com.hashedin.models.responses.BillResponse;
import com.hashedin.repositories.BillRepository;
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

public class IBillsServiceTest {

    @Mock
    private BillRepository billRepository;
    @Mock
    private IGroupsService groupsService;
    @InjectMocks
    private BillsServiceImpl billsService;
    private Bills bills;
    private BillResponse billResponse;
    private BillRequest billRequest;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        billRequest = new BillRequest();
        billRequest.setGroupId(1l);
        billRequest.setPaidBy("rosh@gmail.com");
        billRequest.setAmount(new Double(100));
        billRequest.setSettled(false);
        Map<String,Double> map = new HashMap<>();
        map.put("rosh@gmail.com",new Double(-50));
        map.put("rosh@hashedin.com",new Double(50));
        billRequest.setAmountPerHead(map);
        bills = new Bills();
        bills.setId(1l);
        bills.setGroupId(1l);
        bills.setPaidBy("rosh@gmail.com");
        bills.setAmount(new Double(100));
        bills.setAmountPerHead(map);
        billResponse = new BillResponse();
        BeanUtils.copyProperties(bills,billResponse);
    }

    @Test(expected = BadRequestException.class)
    public void addBillWithRequestNullTest() throws Exception {
        billsService.addBill(null);
    }

    @Test
    public void addBillTest()throws Exception{
        Users user1 = new Users();
        user1.setEmail("rosh@gmail.com");
        user1.setId(1l);
        user1.setName("Rosh1");
        Users user2 = new Users();
        user2.setEmail("rosh@hashedin.com");
        user2.setId(2l);
        user2.setName("Rosh2");
        Groups groups = new Groups();
        groups.setId(1l);
        groups.setGroupName("abc");
        user1.setGroupsList(Arrays.asList(groups));
        user2.setGroupsList(Arrays.asList(groups));
        groups.setMembers(Arrays.asList(user1,user2));
        Mockito.when(billRepository.save(Mockito.any())).thenReturn(bills);
        Mockito.when(groupsService.getGroup(Mockito.anyLong())).thenReturn(groups);
        Assert.assertEquals(billResponse,billsService.addBill(billRequest));
    }

    @Test(expected = BadRequestException.class)
    public void getBillWithIdNullTest() throws Exception{
        billsService.getBill(null);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getBillWithIdNotFoundTest() throws Exception{
        Mockito.when(billRepository.findByIdAndDeletedFalse(Mockito.anyLong())).thenReturn(null);
        billsService.getBill(1l);
    }

    @Test
    public void getBillTest() throws Exception{
        Mockito.when(billRepository.findByIdAndDeletedFalse(Mockito.anyLong())).thenReturn( bills);
        Assert.assertEquals(billResponse,billsService.getBill(1l));;
    }

    @Test(expected = BadRequestException.class)
    public void deleteBillWithIdNullTest() throws Exception{
        billsService.deleteBill(null);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteBillWithIdNotFoundTest() throws Exception{
        Mockito.when(billRepository.findByIdAndDeletedFalse(Mockito.anyLong())).thenReturn(null);
        billsService.deleteBill(1l);
    }

    @Test
    public void deleteBillTest() throws Exception{
        Mockito.when(billRepository.findByIdAndDeletedFalse(Mockito.anyLong())).thenReturn( bills);
        bills.setDeleted(true);
        Mockito.when(billRepository.save(Mockito.any())).thenReturn(bills);
        Assert.assertEquals("Deleted",billsService.deleteBill(1l));;
    }

    @Test(expected = BadRequestException.class)
    public void updateBillWithIdNullTest() throws Exception{
        billsService.updateBill(null,billRequest);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateBillWithIdNotFoundTest() throws Exception{
        Mockito.when(billRepository.findByIdAndDeletedFalse(Mockito.anyLong())).thenReturn(null);
        billsService.updateBill(1l,billRequest);
    }

    @Test
    public void updateBillTest() throws Exception{
        Mockito.when(billRepository.findByIdAndDeletedFalse(Mockito.anyLong())).thenReturn( bills);
        Mockito.when(billRepository.save(Mockito.any())).thenReturn(bills);
        Assert.assertEquals(billResponse,billsService.updateBill(1l,billRequest));
    }

    @Test
    public void settlementBillTest()throws Exception{
        Mockito.when(billRepository.findByIdAndGroupIdAndDeletedFalse(Mockito.anyLong(),Mockito.anyLong()))
                .thenReturn(bills);
        SettlementRequest request = new SettlementRequest();
        request.setAmount(new Double(50));
        request.setBillId(1l);
        request.setGroupId(1l);
        request.setPaidTo("rosh@gmail.com");
        Mockito.when(billRepository.save(Mockito.any())).thenReturn(bills);
        Assert.assertEquals(billResponse,billsService.settlementBill(request));
    }

    @Test(expected = BadRequestException.class)
    public void settlementBillWithRequestNullTest()throws Exception{
       billsService.settlementBill(null);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void settlementBillWithNotFoundTest()throws Exception{
        Mockito.when(billRepository.findByIdAndGroupIdAndDeletedFalse(Mockito.anyLong(),Mockito.anyLong()))
                .thenReturn(null);
        SettlementRequest request = new SettlementRequest();
        request.setAmount(new Double(50));
        request.setBillId(1l);
        request.setGroupId(1l);
        request.setPaidTo("rosh@gmail.com");
        billsService.settlementBill(request);
    }


}
