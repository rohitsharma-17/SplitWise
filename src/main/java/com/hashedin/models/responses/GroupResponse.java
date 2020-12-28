package com.hashedin.models.responses;

import com.hashedin.entities.Users;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("Group detail response")
public class GroupResponse extends BaseResponse {

    @ApiModelProperty("Group Id")
    private Long id;

    @ApiModelProperty("Group name")
    private String groupName;

    @ApiModelProperty("Group member")
    private List<Users> members;
}
