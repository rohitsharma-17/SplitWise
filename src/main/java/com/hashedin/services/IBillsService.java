package com.hashedin.services;

import com.hashedin.entities.Bills;
import com.hashedin.models.requests.BillRequest;
import com.hashedin.models.requests.SettlementRequest;
import com.hashedin.models.responses.BillResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IBillsService {

    BillResponse addBill(BillRequest billRequest) throws Exception;

    BillResponse getBill(Long billId) throws Exception;

    BillResponse updateBill(Long billId, BillRequest billRequest) throws Exception;

    String deleteBill(Long billId) throws Exception;

    BillResponse settlementBill(SettlementRequest request) throws Exception;

    Boolean billSettle(Long billId) throws Exception;

    List<Bills> getListOfBill(Long groupId) throws Exception;
}
