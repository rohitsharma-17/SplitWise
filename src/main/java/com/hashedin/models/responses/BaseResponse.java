package com.hashedin.models.responses;

import lombok.Data;

@Data
public class BaseResponse {

    private int statusCode = 200;

    private String message = "success";
}
