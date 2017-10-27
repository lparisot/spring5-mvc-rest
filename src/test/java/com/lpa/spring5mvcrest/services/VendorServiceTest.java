package com.lpa.spring5mvcrest.services;

import com.lpa.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.lpa.spring5mvcrest.api.v1.model.VendorDTO;
import com.lpa.spring5mvcrest.api.v1.model.VendorListDTO;
import com.lpa.spring5mvcrest.domain.Vendor;
import com.lpa.spring5mvcrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendorServiceTest {
    public static final String NAME = "Vendor ";

    @Mock
    private VendorRepository vendorRepository;
    private VendorService vendorService;

    private Vendor getVendor(long id) {
        Vendor vendor = new Vendor();
        vendor.setName(NAME + id);
        vendor.setId(id);
        return vendor;
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getVendorById() throws Exception {
        //given
        long id = 1L;
        Vendor vendor = getVendor(id);

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(id);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());
        assertThat(vendorDTO.getName(), is(equalTo(NAME + id)));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() throws Exception {
        //given
        given(vendorRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(1L);

        //then
        then(vendorRepository).should(times(1)).findById(anyLong());
    }

    @Test
    public void getAllVendors() throws Exception {
        //given
        List<Vendor> vendors = Arrays.asList(getVendor(1L), getVendor(2L));
        given(vendorRepository.findAll()).willReturn(vendors);

        //when
        VendorListDTO vendorListDTO = vendorService.getAllVendors();

        //then
        then(vendorRepository).should(times(1)).findAll();
        assertThat(vendorListDTO.getVendors().size(), is(equalTo(2)));
    }

    @Test
    public void createNewVendor() throws Exception {
        //given
        long id = 1L;

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME + id);

        Vendor vendor = getVendor(id);

        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        //when
        VendorDTO saveVendorDTO = vendorService.createNewVendor(vendorDTO);

        //then
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(saveVendorDTO.getVendorUrl(), containsString(String.valueOf(id)));
    }

    @Test
    public void saveVendorByDTO() throws Exception {
        //given
        long id = 1L;

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME + id);

        Vendor vendor = getVendor(id);

        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        //when
        VendorDTO saveVendorDTO = vendorService.saveVendorByDTO(id, vendorDTO);

        //then
        then(vendorRepository).should().save(any(Vendor.class));
        assertThat(saveVendorDTO.getVendorUrl(), containsString(String.valueOf(id)));
    }

    @Test
    public void patchVendor() throws Exception {
        //given
        long id = 1L;

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME + id);

        Vendor vendor = getVendor(id);

        given(vendorRepository.findById(anyLong())).willReturn(Optional.of(vendor));
        given(vendorRepository.save(any(Vendor.class))).willReturn(vendor);

        //when
        VendorDTO saveVendorDTO = vendorService.patchVendor(id, vendorDTO);

        //then
        then(vendorRepository).should().save(any(Vendor.class));
        then(vendorRepository).should(times(1)).findById(anyLong());
        assertThat(saveVendorDTO.getVendorUrl(), containsString(String.valueOf(id)));
    }

    @Test
    public void deleteVendorById() throws Exception {
        //given
        Long id = 1L;
        Vendor vendor = getVendor(id);

        when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(vendor));

        //when
        vendorService.deleteVendorById(id);

        //then
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteVendorByIdNotFound() throws Exception {
        vendorService.deleteVendorById(1L);
    }
}