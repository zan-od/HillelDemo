package org.hillel.demo.service;

import org.hillel.demo.model.City;
import org.hillel.demo.repository.CityRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CityServiceImplTest {
    @Mock
    private CityRepository mockedCityRepository;

    private CityService cityService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        cityService = new CityServiceImpl();
        ((CityServiceImpl) cityService).setCityRepository(mockedCityRepository);
    }

    @Test(expected = NullPointerException.class)
    public void testFindCity_NameIsNull_ShouldThrowException() {
        cityService.findCityByName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindCity_NameIsEmpty_ShouldThrowException() {
        cityService.findCityByName(" ");
    }

    @Test
    public void testFindCity_CityNotFound_ShouldReturnNull() {

        when(mockedCityRepository.findByName("Odessa")).thenReturn(null);

        City city = cityService.findCityByName("Odessa");

        assertNull(city);
    }

    @Test(expected = NullPointerException.class)
    public void testGetCityOrCreateNew_NameIsNull_ShouldThrowException() {
        cityService.getCityOrCreateNew(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCityOrCreateNew_NameIsEmpty_ShouldThrowException() {
        cityService.getCityOrCreateNew(" ");
    }

    @Test
    public void testGetCityOrCreateNew_CityNotFound_ShouldCreateNew() {

        City newCity = new City();
        newCity.setName("Odessa");

        when(mockedCityRepository.findByName("Odessa")).thenReturn(null);
        when(mockedCityRepository.save(any())).thenReturn(newCity);

        City savedCity = cityService.getCityOrCreateNew("Odessa");

        verify(mockedCityRepository, times(1)).save(any());
        assertEquals(newCity.getName(), savedCity.getName());
    }

    @Test
    public void testGetCityOrCreateNew_CityFound_ShouldReturnExisting() {

        City city = new City();
        city.setId(1);
        city.setName("Odessa");

        when(mockedCityRepository.findByName("Odessa")).thenReturn(city);

        City savedCity = cityService.getCityOrCreateNew("Odessa");

        verify(mockedCityRepository, times(0)).save(any());
        assertEquals(city, savedCity);
    }

}