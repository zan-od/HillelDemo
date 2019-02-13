package org.hillel.demo.service;

import org.hillel.demo.model.Address;
import org.hillel.demo.model.City;
import org.hillel.demo.model.Country;
import org.hillel.demo.model.Street;
import org.hillel.demo.repository.AddressRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddressServiceImplTest {
    @Mock
    private AddressRepository mockedAddressRepository;

    @Mock
    private CountryService mockedCountryService;

    @Mock
    private CityService mockedCityService;

    @Mock
    private StreetService mockedStreetService;

    private AddressService addressService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        addressService = new AddressServiceImpl();
        ((AddressServiceImpl) addressService).setAddressRepository(mockedAddressRepository);
        ((AddressServiceImpl) addressService).setCountryService(mockedCountryService);
        ((AddressServiceImpl) addressService).setCityService(mockedCityService);
        ((AddressServiceImpl) addressService).setStreetService(mockedStreetService);
    }

    @Test(expected = NullPointerException.class)
    public void testFindAddress_CountryNameIsNull_ShouldThrowException() {
        addressService.findAddress(null, "b", "c");
    }

    @Test(expected = NullPointerException.class)
    public void testFindAddress_CityNameIsNull_ShouldThrowException() {
        addressService.findAddress("a", null, "c");
    }

    @Test(expected = NullPointerException.class)
    public void testFindAddress_StreetNameIsNull_ShouldThrowException() {
        addressService.findAddress("a", "b", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAddress_CountryNameIsEmpty_ShouldThrowException() {
        addressService.findAddress(" ", "b", "c");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAddress_CityNameIsEmpty_ShouldThrowException() {
        addressService.findAddress("a", " ", "c");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAddress_StreetNameIsEmpty_ShouldThrowException() {
        addressService.findAddress("a", "b", " ");
    }

    @Test
    public void testFindAddress_CountryNotFound_ShouldReturnNull() {

        when(mockedCountryService.findCountryByName("Ukraine")).thenReturn(null);
        when(mockedCityService.findCityByName("Odessa")).thenReturn(new City());
        when(mockedStreetService.findStreetByName("Filatova")).thenReturn(new Street());

        Address address = addressService.findAddress("Ukraine", "Odessa", "Filatova");

        assertNull(address);
    }

    @Test
    public void testFindAddress_CityNotFound_ShouldReturnNull() {

        when(mockedCountryService.findCountryByName("Ukraine")).thenReturn(new Country());
        when(mockedCityService.findCityByName("Odessa")).thenReturn(null);
        when(mockedStreetService.findStreetByName("Filatova")).thenReturn(new Street());

        Address address = addressService.findAddress("Ukraine", "Odessa", "Filatova");

        assertNull(address);
    }

    @Test
    public void testFindAddress_StreetNotFound_ShouldReturnNull() {

        when(mockedCountryService.findCountryByName("Ukraine")).thenReturn(new Country());
        when(mockedCityService.findCityByName("Odessa")).thenReturn(new City());
        when(mockedStreetService.findStreetByName("Filatova")).thenReturn(null);

        Address address = addressService.findAddress("Ukraine", "Odessa", "Filatova");

        assertNull(address);
    }

    @Test
    public void testFindAddress_ShouldReturnAddress() {

        Country country = new Country();
        country.setId(1);
        country.setName("Ukraine");

        City city = new City();
        city.setId(2);
        city.setName("Odessa");

        Street street = new Street();
        street.setId(3);
        street.setName("Filatova");

        Address address = new Address();
        address.setId(4);
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);

        when(mockedCountryService.findCountryByName("Ukraine")).thenReturn(country);
        when(mockedCityService.findCityByName("Odessa")).thenReturn(city);
        when(mockedStreetService.findStreetByName("Filatova")).thenReturn(street);
        when(mockedAddressRepository.findByCountryAndCityAndStreet(country, city, street)).thenReturn(address);

        Address savedAddress = addressService.findAddress("Ukraine", "Odessa", "Filatova");

        assertEquals(address, savedAddress);
    }

    @Test
    public void testFindAddress_AddressNotFound_ShouldReturnNull() {

        Country country = new Country();
        country.setId(1);
        country.setName("Ukraine");

        City city = new City();
        city.setId(2);
        city.setName("Odessa");

        Street street = new Street();
        street.setId(3);
        street.setName("Filatova");

        when(mockedCountryService.findCountryByName("Ukraine")).thenReturn(country);
        when(mockedCityService.findCityByName("Odessa")).thenReturn(city);
        when(mockedStreetService.findStreetByName("Filatova")).thenReturn(street);
        when(mockedAddressRepository.findByCountryAndCityAndStreet(country, city, street)).thenReturn(null);

        Address savedAddress = addressService.findAddress("Ukraine", "Odessa", "Filatova");

        assertNull(savedAddress);
    }

    @Test(expected = NullPointerException.class)
    public void testGetAddressOrCreateNew_CountryNameIsNull_ShouldThrowException() {
        addressService.getAddressOrCreateNew(null, "b", "c");
    }

    @Test(expected = NullPointerException.class)
    public void testGetAddressOrCreateNew_CityNameIsNull_ShouldThrowException() {
        addressService.getAddressOrCreateNew("a", null, "c");
    }

    @Test(expected = NullPointerException.class)
    public void testGetAddressOrCreateNew_StreetNameIsNull_ShouldThrowException() {
        addressService.getAddressOrCreateNew("a", "b", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAddressOrCreateNew_CountryNameIsEmpty_ShouldThrowException() {
        addressService.getAddressOrCreateNew(" ", "b", "c");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAddressOrCreateNew_CityNameIsEmpty_ShouldThrowException() {
        addressService.getAddressOrCreateNew("a", " ", "c");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAddressOrCreateNew_StreetNameIsEmpty_ShouldThrowException() {
        addressService.getAddressOrCreateNew("a", "b", " ");
    }

    @Test
    public void testGetAddressOrCreateNew_AddressNotFound_ShouldCreateNew() {

        Country country = new Country();
        country.setId(1);
        country.setName("Ukraine");

        City city = new City();
        city.setId(2);
        city.setName("Odessa");

        Street street = new Street();
        street.setId(3);
        street.setName("Filatova");

        Address address = new Address();
        address.setId(4);
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);

        when(mockedCountryService.getCountryOrCreateNew("Ukraine")).thenReturn(country);
        when(mockedCityService.getCityOrCreateNew("Odessa")).thenReturn(city);
        when(mockedStreetService.getStreetOrCreateNew("Filatova")).thenReturn(street);
        when(mockedAddressRepository.findByCountryAndCityAndStreet(country, city, street)).thenReturn(null);
        when(mockedAddressRepository.save(any())).thenReturn(address);

        Address savedAddress = addressService.getAddressOrCreateNew("Ukraine", "Odessa", "Filatova");

        verify(mockedAddressRepository, times(1)).save(any());
        assertEquals(address, savedAddress);
    }

    @Test
    public void testGetCityOrCreateNew_CityFound_ShouldReturnExisting() {

        Country country = new Country();
        country.setId(1);
        country.setName("Ukraine");

        City city = new City();
        city.setId(2);
        city.setName("Odessa");

        Street street = new Street();
        street.setId(3);
        street.setName("Filatova");

        Address address = new Address();
        address.setId(4);
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);

        when(mockedCountryService.getCountryOrCreateNew("Ukraine")).thenReturn(country);
        when(mockedCityService.getCityOrCreateNew("Odessa")).thenReturn(city);
        when(mockedStreetService.getStreetOrCreateNew("Filatova")).thenReturn(street);
        when(mockedAddressRepository.findByCountryAndCityAndStreet(country, city, street)).thenReturn(address);

        Address savedAddress = addressService.getAddressOrCreateNew("Ukraine", "Odessa", "Filatova");

        verify(mockedAddressRepository, times(0)).save(any());
        assertEquals(address, savedAddress);
    }

}