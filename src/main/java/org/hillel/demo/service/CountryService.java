package org.hillel.demo.service;

import org.hillel.demo.model.Country;

public interface CountryService {

    Country findCountryByName(String name);

    Country getCountryOrCreateNew(String name);

}
