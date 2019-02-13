package org.hillel.demo.repository;

import org.hillel.demo.model.Address;
import org.hillel.demo.model.City;
import org.hillel.demo.model.Country;
import org.hillel.demo.model.Street;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    Address findByCountryAndCityAndStreet(Country country, City city, Street street);

}
