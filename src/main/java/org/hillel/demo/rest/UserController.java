package org.hillel.demo.rest;

import org.hillel.demo.model.User;
import org.hillel.demo.rest.dto.AbstractRestResponse;
import org.hillel.demo.rest.dto.RestResponse;
import org.hillel.demo.rest.dto.UserData;
import org.hillel.demo.rest.dto.UserRestResponse;
import org.hillel.demo.service.AddressNotExistException;
import org.hillel.demo.service.UserExistsException;
import org.hillel.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/userinfo", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public @ResponseBody
    RestResponse listUsers(){
        RestResponse response = new RestResponse();
        response.setStatus(200);
        response.setData(userService.listUsers());

        return response;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    RestResponse addUser(@RequestBody UserData userData){

        RestResponse response = new RestResponse();

        if (userData == null){
            response.setStatus(400);
            response.setMessage("Error - user data is empty!");

            return response;
        }

        User user;
        try {
            user = userService.addUser(userData);
        } catch (AddressNotExistException e) {

            response.setStatus(406);
            response.setMessage(String.format("Address %s %s %s doesn't exist.", userData.getCountry(), userData.getCity(), userData.getStreet()));

            return response;

        } catch (UserExistsException e) {

            response.setStatus(204);
            response.setMessage(String.format("User %s %s already exists.", userData.getFirstname(), userData.getLastname()));

            return response;

        }

        if (user == null){
            response.setStatus(500);
            response.setMessage("Error creating user.");

            return response;
        }

        response.setStatus(201);
        response.setMessage(String.format("User %s %s was created.", user.getFirstName(), user.getLastName()));
        response.setData(user);

        return response;
    }

    @RequestMapping(value = "/get/{firstName}/{lastName}", method = RequestMethod.GET)
    public @ResponseBody
    AbstractRestResponse findUser(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName){

        if ((firstName == null) || (lastName == null)){
            RestResponse response = new RestResponse();
            response.setStatus(400);
            response.setMessage("Error - user name is empty!");

            return response;
        }

        User user = userService.findUser(firstName, lastName);
        if (user == null){
            RestResponse response = new RestResponse();
            response.setStatus(204);
            response.setMessage(String.format("User %s %s doesn't exist.", firstName, lastName));

            return response;
        }

        UserRestResponse response = new UserRestResponse();
        response.setStatus(200);
        response.setFirstname(user.getFirstName());
        response.setLastname(user.getLastName());
        response.setCountry(user.getAddress().getCountry().getName());
        response.setCity(user.getAddress().getCity().getName());
        response.setStreet(user.getAddress().getStreet().getName());

        return response;

    }

}
