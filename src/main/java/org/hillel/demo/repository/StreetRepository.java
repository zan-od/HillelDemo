package org.hillel.demo.repository;

import org.hillel.demo.model.Street;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetRepository extends JpaRepository<Street, Integer> {

    Street findByName(String name);

}
