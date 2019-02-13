package org.hillel.demo.service;

import org.hillel.demo.repository.StreetRepository;
import org.hillel.demo.model.Street;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StreetServiceImplTest {
    @Mock
    private StreetRepository mockedStreetRepository;

    private StreetService streetService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        streetService = new StreetServiceImpl();
        ((StreetServiceImpl) streetService).setStreetRepository(mockedStreetRepository);
    }

    @Test(expected = NullPointerException.class)
    public void testFindStreet_NameIsNull_ShouldThrowException() {
        streetService.findStreetByName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindStreet_NameIsEmpty_ShouldThrowException() {
        streetService.findStreetByName(" ");
    }

    @Test
    public void testFindStreet_StreetNotFound_ShouldReturnNull() {

        when(mockedStreetRepository.findByName("Filatova")).thenReturn(null);

        Street street = streetService.findStreetByName("Filatova");

        assertNull(street);
    }

    @Test(expected = NullPointerException.class)
    public void testGetStreetOrCreateNew_NameIsNull_ShouldThrowException() {
        streetService.getStreetOrCreateNew(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetStreetOrCreateNew_NameIsEmpty_ShouldThrowException() {
        streetService.getStreetOrCreateNew(" ");
    }

    @Test
    public void testGetStreetOrCreateNew_StreetNotFound_ShouldCreateNew() {

        Street newStreet = new Street();
        newStreet.setName("Filatova");

        when(mockedStreetRepository.findByName("Filatova")).thenReturn(null);
        when(mockedStreetRepository.save(any())).thenReturn(newStreet);

        Street savedStreet = streetService.getStreetOrCreateNew("Filatova");

        verify(mockedStreetRepository, times(1)).save(any());
        assertEquals(newStreet.getName(), savedStreet.getName());
    }

    @Test
    public void testGetStreetOrCreateNew_StreetFound_ShouldReturnExisting() {

        Street street = new Street();
        street.setId(1);
        street.setName("Filatova");

        when(mockedStreetRepository.findByName("Filatova")).thenReturn(street);

        Street savedStreet = streetService.getStreetOrCreateNew("Filatova");

        verify(mockedStreetRepository, times(0)).save(any());
        assertEquals(street, savedStreet);
    }


}