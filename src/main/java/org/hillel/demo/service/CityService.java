package org.hillel.demo.service;

import org.hillel.demo.model.City;

public interface CityService {

    City findCityByName(String name);

    City getCityOrCreateNew(String name);

}
