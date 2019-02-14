package org.hillel.demo.service;

import org.hillel.demo.model.User;
import org.hillel.demo.rest.dto.UserData;

import java.util.List;

public interface UserService {

    User addUser(UserData userData) throws AddressNotExistException, UserExistsException;

    User findUser(String firstName, String lastName);

    List<User> listUsers();

}
