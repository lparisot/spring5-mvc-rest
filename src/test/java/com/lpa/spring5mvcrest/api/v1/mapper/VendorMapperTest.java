package com.lpa.spring5mvcrest.api.v1.mapper;

import com.lpa.spring5mvcrest.api.v1.model.VendorDTO;
import com.lpa.spring5mvcrest.domain.Vendor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {
    public final String NAME = "some name";

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void vendorToVendorDTO() throws Exception {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        //then
        assertEquals(vendor.getName(), vendorDTO.getName());
    }

    @Test
    public void vendorDTOToVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        //when
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), vendor.getName());
    }

}