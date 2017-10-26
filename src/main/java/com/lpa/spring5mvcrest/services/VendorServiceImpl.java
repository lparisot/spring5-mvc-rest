package com.lpa.spring5mvcrest.services;

import com.lpa.spring5mvcrest.api.v1.mapper.VendorMapper;
import com.lpa.spring5mvcrest.api.v1.model.VendorDTO;
import com.lpa.spring5mvcrest.api.v1.model.VendorListDTO;
import com.lpa.spring5mvcrest.repositories.VendorRepository;

public class VendorServiceImpl implements VendorService {
    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return null;
    }

    @Override
    public VendorListDTO getAllVendors() {
        return null;
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return null;
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        return null;
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return null;
    }

    @Override
    public void deleteVendorById(Long id) {

    }
}
