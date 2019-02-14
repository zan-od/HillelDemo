package org.hillel.demo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hillel.demo.model.*;
import org.hillel.demo.rest.dto.UserData;
import org.hillel.demo.service.AddressNotExistException;
import org.hillel.demo.service.UserExistsException;
import org.hillel.demo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private UserService mockedUserService;

    private MockMvc mockMvc;

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testFindUser_UserFound() throws Exception {

        Country country = new Country();
        country.setName("Ukraine");

        City city = new City();
        city.setName("Odessa");

        Street street = new Street();
        street.setName("Filatova");

        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);

        User user = new User();
        user.setId(1);
        user.setFirstName("Vasya");
        user.setLastName("Ivanov");
        user.setAddress(address);

        when(mockedUserService.findUser("Vasya", "Ivanov")).thenReturn(user);

        this.mockMvc.perform(get("/userinfo/get/{firstName}/{lastName}", "Vasya", "Ivanov")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.firstname").value("Vasya"))
                .andExpect(jsonPath("$.lastname").value("Ivanov"))
                .andExpect(jsonPath("$.country").value("Ukraine"))
                .andExpect(jsonPath("$.city").value("Odessa"))
                .andExpect(jsonPath("$.street").value("Filatova"));
    }

    @Test
    public void testFindUser_UserNotFound() throws Exception {

        when(mockedUserService.findUser("Vasya", "Ivanov")).thenReturn(null);

        this.mockMvc.perform(get("/userinfo/get/{firstName}/{lastName}", "Vasya", "Ivanov")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(204))
                .andExpect(jsonPath("$.message").value("User Vasya Ivanov doesn't exist."))
                ;
    }

    @Test
    public void testAddUser_UserAlreadyExists() throws Exception {

        UserData userData = new UserData();
        userData.setFirstname("Vasya");
        userData.setLastname("Ivanov");

        when(mockedUserService.addUser(userData)).thenThrow(UserExistsException.class);

        this.mockMvc.perform(post("/userinfo/add", userData)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userData))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(204))
                .andExpect(jsonPath("$.message").value("User Vasya Ivanov already exists."))
        ;
    }

    @Test
    public void testAddUser_AddressNotExist() throws Exception {

        UserData userData = new UserData();
        userData.setFirstname("Vasya");
        userData.setLastname("Ivanov");
        userData.setCountry("Ukraine");
        userData.setCity("Odessa");
        userData.setStreet("Filatova");

        when(mockedUserService.addUser(userData)).thenThrow(AddressNotExistException.class);

        this.mockMvc.perform(post("/userinfo/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userData))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(406))
                .andExpect(jsonPath("$.message").value("Address Ukraine Odessa Filatova doesn't exist."))
        ;
    }

    @Test
    public void testAddUser() throws Exception {

        UserData userData = new UserData();
        userData.setFirstname("Vasya");
        userData.setLastname("Ivanov");
        userData.setCountry("Ukraine");
        userData.setCity("Odessa");
        userData.setStreet("Filatova");

        Country country = new Country();
        country.setName("Ukraine");

        City city = new City();
        city.setName("Odessa");

        Street street = new Street();
        street.setName("Filatova");

        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);

        User user = new User();
        user.setId(1);
        user.setFirstName("Vasya");
        user.setLastName("Ivanov");
        user.setAddress(address);

        when(mockedUserService.addUser(userData)).thenReturn(user);

        this.mockMvc.perform(post("/userinfo/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userData))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("User Vasya Ivanov was created."))
                //.andExpect(jsonPath("$.data").value(asJsonString(user)))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.firstName").value("Vasya"))
                .andExpect(jsonPath("$.data.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.data.address.country.name").value("Ukraine"))
                .andExpect(jsonPath("$.data.address.city.name").value("Odessa"))
                .andExpect(jsonPath("$.data.address.street.name").value("Filatova"))
        ;
    }
}