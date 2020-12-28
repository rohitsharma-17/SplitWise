package com.hashedin.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hashedin.entities.Users;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("Group detail request")
public class GroupRequest {

    @ApiModelProperty("Group name")
    private String groupName;

    @ApiModelProperty("Group members")
    private List<Users> members;

}
