package org.hillel.demo.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RestResponse extends AbstractRestResponse{

    @NotNull
    private int status;

    private String message;

    private Object data;

}
