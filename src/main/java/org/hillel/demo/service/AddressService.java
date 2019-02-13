package org.hillel.demo.service;

import org.hillel.demo.model.Address;

public interface AddressService {

    Address getAddressOrCreateNew(String countryName, String cityName, String streetName);

}
