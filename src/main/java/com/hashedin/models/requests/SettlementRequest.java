package com.hashedin.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Bill detail request")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettlementRequest {

    @ApiModelProperty("Bill id")
    private Long billId;

    @ApiModelProperty("Group id")
    private Long groupId;

    @ApiModelProperty("Amount")
    private Double amount;

    @ApiModelProperty("Paid to")
    private String paidTo;
}
