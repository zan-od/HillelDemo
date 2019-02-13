package org.hillel.demo.service;

import org.hillel.demo.model.Street;

public interface StreetService {

    Street getStreetOrCreateNew(String name);

}
