package org.hillel.demo.service;

import org.hillel.demo.model.Street;
import org.hillel.demo.repository.StreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreetServiceImpl implements StreetService{

    private StreetRepository streetRepository;

    @Autowired
    public void setStreetRepository(StreetRepository streetRepository){
        this.streetRepository = streetRepository;
    }

    private void assertName(String name) {
        if (name == null)
            throw new NullPointerException();
        if (name.trim().isEmpty())
            throw new IllegalArgumentException();
    }

    @Override
    public Street findStreetByName(String name) {
        assertName(name);

        return streetRepository.findByName(name);
    }

    @Override
    public Street getStreetOrCreateNew(String name) {
        assertName(name);

        Street street = findStreetByName(name);
        if (street == null){
            street = new Street();
            street.setName(name);
            street = streetRepository.save(street);
        }

        return street;
    }

}
