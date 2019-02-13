package org.hillel.demo.service;

import org.hillel.demo.model.Country;
import org.hillel.demo.repository.CountryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class CountryServiceImplTest {

    @Mock
    private CountryRepository mockedCountryRepository;

    private CountryService countryService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        countryService = new CountryServiceImpl();
        ((CountryServiceImpl) countryService).setCountryRepository(mockedCountryRepository);
    }

    @Test(expected = NullPointerException.class)
    public void testFindCountry_NameIsNull_ShouldThrowException() {
        countryService.findCountryByName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindCountry_NameIsEmpty_ShouldThrowException() {
        countryService.findCountryByName(" ");
    }

    @Test
    public void testFindCountry_CountryNotFound_ShouldReturnNull() {

        when(mockedCountryRepository.findByName("Ukraine")).thenReturn(null);

        Country country = countryService.findCountryByName("Ukraine");

        assertNull(country);
    }

    @Test(expected = NullPointerException.class)
    public void testGetCountryOrCreateNew_NameIsNull_ShouldThrowException() {
        countryService.getCountryOrCreateNew(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCountryOrCreateNew_NameIsEmpty_ShouldThrowException() {
        countryService.getCountryOrCreateNew(" ");
    }

    @Test
    public void testGetCountryOrCreateNew_CountryNotFound_ShouldCreateNew() {

        Country newCountry = new Country();
        newCountry.setName("Ukraine");

        when(mockedCountryRepository.findByName("Ukraine")).thenReturn(null);
        when(mockedCountryRepository.save(any())).thenReturn(newCountry);

        Country savedCountry = countryService.getCountryOrCreateNew("Ukraine");

        verify(mockedCountryRepository, times(1)).save(any());
        assertEquals(newCountry.getName(), savedCountry.getName());
    }

    @Test
    public void testGetCountryOrCreateNew_CountryFound_ShouldReturnExisting() {

        Country country = new Country();
        country.setId(1);
        country.setName("Ukraine");

        when(mockedCountryRepository.findByName("Ukraine")).thenReturn(country);

        Country savedCountry = countryService.getCountryOrCreateNew("Ukraine");

        verify(mockedCountryRepository, times(0)).save(any());
        assertEquals(country, savedCountry);
    }

}
