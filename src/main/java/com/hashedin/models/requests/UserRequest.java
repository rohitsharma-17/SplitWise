package com.hashedin.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hashedin.entities.Groups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("User detail request")
public class UserRequest {

    @ApiModelProperty("name")
    private String name;

    @ApiModelProperty("email")
    private String email;

    @ApiModelProperty("group list")
    private List<Groups> groupsList;
}
