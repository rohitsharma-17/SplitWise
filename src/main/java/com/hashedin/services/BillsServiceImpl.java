package com.hashedin.services;

import com.hashedin.entities.Bills;
import com.hashedin.entities.Groups;
import com.hashedin.exceptions.BadRequestException;
import com.hashedin.exceptions.ResourceNotFoundException;
import com.hashedin.models.requests.BillRequest;
import com.hashedin.models.requests.SettlementRequest;
import com.hashedin.models.responses.BillResponse;
import com.hashedin.repositories.BillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BillsServiceImpl implements IBillsService{
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private IGroupsService groupsService;
    @Override
    public BillResponse addBill(BillRequest billRequest) throws Exception {
        if(null == billRequest){
            log.error("Request is null");
            throw new BadRequestException("Request is null");
        }
        Bills bills = new Bills();
        Groups groups = groupsService.getGroup(billRequest.getGroupId());
        bills.setAmount(billRequest.getAmount());
        bills.setPaidBy(billRequest.getPaidBy());
        bills.setGroupId(billRequest.getGroupId());
        if (null != groups.getMembers()) {
            Map<String, Double> amounts = new HashMap<>();
            groups.getMembers().stream().filter(users ->
                !users.getEmail().equalsIgnoreCase(billRequest.getPaidBy())
            ).collect(Collectors.toList()).forEach(users -> {
                amounts.put(users.getEmail(),billRequest.getAmount()/groups.getMembers().size());
            });
            amounts.put(billRequest.getPaidBy(), billRequest.getAmount()/groups.getMembers().size() - billRequest.getAmount());
            bills.setAmountPerHead(amounts);
        }
        BillResponse billResponse = new BillResponse();
        BeanUtils.copyProperties(billRepository.save(bills),billResponse);
        return billResponse;
    }

    @Override
    public BillResponse getBill(Long billId) throws Exception {
        if(null == billId){
            log.error("id is null");
            throw new BadRequestException("Id is null");
        }
        Bills bills = billRepository.findByIdAndDeletedFalse(billId);
        if(null == bills){
            log.error("Bill not Found");
            throw new ResourceNotFoundException("Bill not Found");
        }
        BillResponse billResponse = new BillResponse();
        BeanUtils.copyProperties(bills,billResponse);
        return billResponse;
    }

    @Override
    public BillResponse updateBill(Long billId, BillRequest billRequest) throws Exception {
        if(null == billId || null == billRequest){
            log.error("Id or request is null");
            throw new BadRequestException("Id or request is null");
        }
        Bills bills = billRepository.findByIdAndDeletedFalse(billId);
        if(null == bills){
            log.error("Bill not Found");
            throw new ResourceNotFoundException("Bill not Found");
        }
        BeanUtils.copyProperties(billRequest,bills);
        billRepository.save(bills);
        BillResponse billResponse = new BillResponse();
        BeanUtils.copyProperties(bills,billResponse);
        return billResponse;
    }

    @Override
    public String deleteBill(Long billId) throws Exception {
        if(null == billId){
            log.error("Id is null");
            throw new BadRequestException("Id is null");
        }
        Bills bills = billRepository.findByIdAndDeletedFalse(billId);
        if(null == bills){
            log.error("Bill not Found");
            throw new ResourceNotFoundException("Bill not Found");
        }
        bills.setDeleted(true);
        billRepository.save(bills);
        return "Deleted";
    }

    @Override
    public BillResponse settlementBill(SettlementRequest request) throws Exception {
        if (null == request || null == request.getBillId() || null == request.getGroupId()){
            log.error("Id is null");
            throw new BadRequestException("Id is null");
        }
        Bills bills = billRepository.findByIdAndGroupIdAndDeletedFalse(request.getBillId(),request.getGroupId());
        if(null == bills){
            log.error("Bill not Found");
            throw new ResourceNotFoundException("Bill not found");
        }
        bills.getAmountPerHead().forEach((k,v) ->{
            if(k.equalsIgnoreCase(request.getPaidTo())){
                bills.getAmountPerHead().put(request.getPaidTo(), v + request.getAmount());
            }
        });
        BillResponse billResponse = new BillResponse();
        BeanUtils.copyProperties(billRepository.save(bills),billResponse);
        return billResponse;
    }

    @Override
    public Boolean billSettle(Long billId) throws Exception {
        if(null == billId){
            log.error("Id is null");
            throw new BadRequestException("Id is null");
        }
        Bills bills = billRepository.findByIdAndDeletedFalse(billId);
        if(null == bills){
            log.error("Bill not Found");
            throw new ResourceNotFoundException("Bill not Found");
        }
        return bills.getSettled();
    }

    @Override
    public List<Bills> getListOfBill(Long groupId) throws Exception {
        if(null == groupId){
            log.error("Id is null");
            throw new Exception("Id not found");
        }
        List<Bills> billsList = billRepository.findByGroupIdAndDeletedFalse(groupId);
        if(null == billsList){
            log.error("Bill not Found");
            throw new ResourceNotFoundException("Bill not found");
        }
        return billsList;
    }
}
