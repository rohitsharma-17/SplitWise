package com.hashedin.controllers;

import com.hashedin.models.requests.GroupRequest;
import com.hashedin.models.responses.BaseResponse;
import com.hashedin.models.responses.GroupResponse;
import com.hashedin.services.IGroupsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private IGroupsService groupsService;

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@RequestBody GroupRequest groupRequest) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(groupsService.createGroup(groupRequest));
    }

    @GetMapping("/addmembers")
    public ResponseEntity<BaseResponse> addMemberToGroup(@RequestParam String email,@RequestParam Long groupId) throws Exception{
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage(groupsService.addMemberInGroup(email,groupId));
        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

    @GetMapping("/simplifiedDebt")
    public ResponseEntity<Map<String,Double>> simplifiedDebt(@RequestParam Long groupId) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(groupsService.getSimplifiedDebt(groupId));
    }


}
