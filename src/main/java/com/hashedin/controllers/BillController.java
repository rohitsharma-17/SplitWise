package com.hashedin.controllers;

import com.hashedin.models.requests.BillRequest;
import com.hashedin.models.requests.SettlementRequest;
import com.hashedin.models.responses.BillResponse;
import com.hashedin.services.IBillsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private IBillsService billsService;

    @PostMapping
    public ResponseEntity<BillResponse> addBill(@RequestBody BillRequest billRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(billsService.addBill(billRequest));
    }

    @DeleteMapping("/{billId}")
    public ResponseEntity<String> deleteBill(@PathVariable("billId") Long billId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(billsService.deleteBill(billId));
    }

    @GetMapping("/{billId}")
    public ResponseEntity<BillResponse> getBill(@PathVariable("billId") Long billId) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(billsService.getBill(billId));
    }

    @PutMapping("/{billId}")
    public ResponseEntity<BillResponse> updateBill(@PathVariable("billId") Long billId, @RequestBody BillRequest billRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(billsService.updateBill(billId, billRequest));
    }

    @PostMapping("/settlementbills")
    public ResponseEntity<BillResponse> settlementBill(@RequestBody SettlementRequest request) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(billsService.settlementBill(request));
    }
}
