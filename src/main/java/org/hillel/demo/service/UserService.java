package org.hillel.demo.service;

import org.hillel.demo.model.User;

public interface UserService {

    User addUser(User user);

    User findUser(String firstName, String lastName);

}
