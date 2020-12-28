package com.hashedin.models.responses;

import com.hashedin.converters.AmountPerUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@ApiModel("Bill detail response")
public class BillResponse extends BaseResponse{

    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("Amount")
    private Double amount;

    @ApiModelProperty("Upload at")
    private LocalDateTime uploadAt;

    @ApiModelProperty("Paid by")
    private String paidBy;

    @ApiModelProperty("Settled")
    private Boolean settled;

    @ApiModelProperty("GroupId")
    private Long groupId;

    @ApiModelProperty("amount per head")
    private Map<String,Double> amountPerHead;
}
