package org.hillel.demo.repository;

import org.hillel.demo.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {

    City findByName(String name);

}
