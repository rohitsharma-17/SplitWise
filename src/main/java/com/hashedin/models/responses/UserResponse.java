package com.hashedin.models.responses;

import com.hashedin.entities.Groups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("Group detail response")
public class UserResponse  extends BaseResponse{

    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("Name")
    private String name;

    @ApiModelProperty("Email")
    private String email;

    @ApiModelProperty("Groups list")
    private List<Groups> groupsList;
}
