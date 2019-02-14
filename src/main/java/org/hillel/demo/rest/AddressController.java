package org.hillel.demo.rest;

import org.hillel.demo.model.Address;
import org.hillel.demo.rest.dto.AddressData;
import org.hillel.demo.rest.dto.RestResponse;
import org.hillel.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/address", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController {

    private AddressService addressService;

    @Autowired
    public void setAddressService(AddressService addressService){
        this.addressService = addressService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public @ResponseBody
    RestResponse listAddresses(){
        RestResponse response = new RestResponse();
        response.setStatus(200);
        response.setData(addressService.listAddresses());

        return response;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public //@ResponseBody
    RestResponse addAddress(@RequestBody AddressData addressData){
        RestResponse response = new RestResponse();

        if (addressData == null){
            response.setStatus(400);
            response.setMessage("Error - address data is empty!");

            return response;
        }

        Address address = addressService.getAddressOrCreateNew(addressData.getCountry(), addressData.getCity(), addressData.getStreet());

        if (address == null){
            response.setStatus(500);
            response.setMessage("Error creating address - internal server error!");

            return response;
        }

        response.setStatus(201);
        response.setMessage(String.format("Address {%s} is created", address.toFormattedString()));
        response.setData(address);

        return response;
    }


}
