package com.hashedin.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("Bill detail request")
public class BillRequest {

    @ApiModelProperty("bill amount")
    private Double amount;

    @ApiModelProperty("paid by")
    private String paidBy;

    @ApiModelProperty("settled")
    private Boolean settled;

    @ApiModelProperty("groupId")
    private Long groupId;

    @ApiModelProperty("amount per head")
    private Map<String,Double> amountPerHead;
}
