package org.hillel.demo.service;

import org.hillel.demo.model.Country;
import org.hillel.demo.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService{

    private CountryRepository countryRepository;

    @Autowired
    public void setCountryRepository(CountryRepository countryRepository){
        this.countryRepository = countryRepository;
    }

    private void assertName(String name) {
        if (name == null)
            throw new NullPointerException();
        if (name.trim().isEmpty())
            throw new IllegalArgumentException();
    }

    @Override
    public Country findCountryByName(String name) {
        assertName(name);

        return countryRepository.findByName(name);
    }

    @Override
    public Country getCountryOrCreateNew(String name) {
        assertName(name);

        Country country = findCountryByName(name);
        if (country == null){
            country = new Country();
            country.setName(name);
            country = countryRepository.save(country);
        }

        return country;
    }
}
