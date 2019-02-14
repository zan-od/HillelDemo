package org.hillel.demo.service;

import org.hillel.demo.model.Address;
import org.hillel.demo.model.User;
import org.hillel.demo.repository.UserRepository;
import org.hillel.demo.rest.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private AddressService addressService;
    private UserRepository userRepository;

    @Autowired
    public void setAddressService(AddressService addressService){
        this.addressService = addressService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(UserData userData) throws AddressNotExistException, UserExistsException {

        User user = findUser(userData.getFirstname(), userData.getLastname());
        if (user != null){
            throw new UserExistsException();
        }

        Address address = addressService.findAddress(userData.getCountry(), userData.getCity(), userData.getStreet());
        if (address == null){
            throw new AddressNotExistException();
        }

        user = new User();
        user.setFirstName(userData.getFirstname());
        user.setLastName(userData.getLastname());
        user.setAddress(address);

        return userRepository.save(user);
    }

    @Override
    public User findUser(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

}
