package org.hillel.demo.repository;

import org.hillel.demo.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    Country findByName(String name);

}
