package org.hillel.demo.repository;

import org.hillel.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByFirstNameAndLastName(String firstName, String lastName);

}
