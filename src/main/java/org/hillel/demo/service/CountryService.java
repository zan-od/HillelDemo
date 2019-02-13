package org.hillel.demo.service;

import org.hillel.demo.model.Country;

public interface CountryService {

    Country getCountryOrCreateNew(String name);

}
