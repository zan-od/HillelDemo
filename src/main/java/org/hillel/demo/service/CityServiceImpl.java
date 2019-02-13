package org.hillel.demo.service;

import org.hillel.demo.model.City;
import org.hillel.demo.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService{

    private CityRepository cityRepository;

    @Autowired
    public void setCityRepository(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }

    private void assertName(String name) {
        if (name == null)
            throw new NullPointerException();
        if (name.trim().isEmpty())
            throw new IllegalArgumentException();
    }

    @Override
    public City findCityByName(String name) {
        assertName(name);

        return cityRepository.findByName(name);
    }

    @Override
    public City getCityOrCreateNew(String name) {
        assertName(name);

        City city = findCityByName(name);
        if (city == null){
            city = new City();
            city.setName(name);
            city = cityRepository.save(city);
        }

        return city;
    }

}
