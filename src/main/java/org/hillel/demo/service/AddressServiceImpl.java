package org.hillel.demo.service;

import org.hillel.demo.model.Address;
import org.hillel.demo.model.City;
import org.hillel.demo.model.Country;
import org.hillel.demo.model.Street;
import org.hillel.demo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{

    private AddressRepository addressRepository;
    private CountryService countryService;
    private CityService cityService;
    private StreetService streetService;

    @Autowired
    public void setAddressRepository(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    @Autowired
    public void setCountryService(CountryService countryService){
        this.countryService = countryService;
    }

    @Autowired
    public void setCityService(CityService cityService){
        this.cityService = cityService;
    }

    @Autowired
    public void setStreetService(StreetService streetService){
        this.streetService = streetService;
    }

    private void assertParameter(String name, String parameterName) {
        if (name == null)
            throw new NullPointerException(String.format("Parameter %s is null", parameterName));
        if (name.trim().isEmpty())
            throw new IllegalArgumentException(String.format("Parameter %s is empty", parameterName));
    }

    @Override
    public Address findAddress(String countryName, String cityName, String streetName) {
        assertParameter(countryName, "countryName");
        assertParameter(cityName, "cityName");
        assertParameter(streetName, "streetName");

        Country country = countryService.findCountryByName(countryName);
        if (country == null){
            return null;
        }

        City city = cityService.findCityByName(cityName);
        if (city == null){
            return null;
        }

        Street street = streetService.findStreetByName(streetName);
        if (street == null){
            return null;
        }

        return addressRepository.findByCountryAndCityAndStreet(country, city, street);
    }

    @Override
    public Address getAddressOrCreateNew(String countryName, String cityName, String streetName) {
        assertParameter(countryName, "countryName");
        assertParameter(cityName, "cityName");
        assertParameter(streetName, "streetName");

        Country country = countryService.getCountryOrCreateNew(countryName);
        City city = cityService.getCityOrCreateNew(cityName);
        Street street = streetService.getStreetOrCreateNew(streetName);

        Address address = addressRepository.findByCountryAndCityAndStreet(country, city, street);
        if (address == null){
            address = new Address();
            address.setCountry(country);
            address.setCity(city);
            address.setStreet(street);

            address = addressRepository.save(address);
        }

        return address;
    }

}
