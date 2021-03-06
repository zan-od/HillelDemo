package org.hillel.demo.service;

import org.hillel.demo.model.Address;

import java.util.List;

public interface AddressService {

    Address findAddress(String countryName, String cityName, String streetName);

    Address getAddressOrCreateNew(String countryName, String cityName, String streetName);

    List<Address> listAddresses();

}
