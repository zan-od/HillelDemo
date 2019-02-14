package org.hillel.demo.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressData {

    @NotNull
    private String country;

    @NotNull
    private String city;

    @NotNull
    private String street;

}
