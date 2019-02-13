package org.hillel.demo.service;

import org.hillel.demo.model.Street;

public interface StreetService {

    Street findStreetByName(String name);

    Street getStreetOrCreateNew(String name);

}
