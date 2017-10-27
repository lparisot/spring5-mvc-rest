package com.lpa.spring5mvcrest.controllers.v1;

import com.lpa.spring5mvcrest.api.v1.model.VendorDTO;
import com.lpa.spring5mvcrest.api.v1.model.VendorListDTO;
import com.lpa.spring5mvcrest.services.VendorService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerTest extends AbstractRestControllerTest {
    @MockBean
    private VendorService vendorService;
    @Autowired
    private MockMvc mockMvc;
    private VendorDTO vendorDTO1;
    private VendorDTO vendorDTO2;

    @Before
    public void setUp() throws Exception {
        vendorDTO1 = new VendorDTO("Vendor 1", VendorController.BASE_URL + "/1");
        vendorDTO2 = new VendorDTO("Vendor 2", VendorController.BASE_URL + "/2");
    }

    @Test
    public void getVendors() throws Exception {
        //given
        VendorListDTO vendors = new VendorListDTO(Arrays.asList(vendorDTO1, vendorDTO2));

        given(vendorService.getAllVendors()).willReturn(vendors);

        //when/then
        mockMvc.perform(get(VendorController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getVendorById() throws Exception {
        //given
        given(vendorService.getVendorById(anyLong())).willReturn(vendorDTO1);

        //when/then
        mockMvc.perform(get(VendorController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())));
    }

    @Test
    public void createNewVendor() throws Exception {
        //given
        given(vendorService.createNewVendor(vendorDTO1)).willReturn(vendorDTO1);

        //when/then
        mockMvc.perform(post(VendorController.BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(vendorDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())));
    }

    @Test
    public void updateVendor() throws Exception {
        //given
        given(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).willReturn(vendorDTO1);

        //when/then
        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(vendorDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO1.getName())));
    }

    @Test
    public void patchVendor() throws Exception {
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName("Update name");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());
        returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");

        given(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).willReturn(returnDTO);

        //when/then
        mockMvc.perform(patch(VendorController.BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendor.getName())))
                .andExpect(jsonPath("$.vendor_url", Matchers.equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void deleteVendor() throws Exception {
        //when
        mockMvc.perform(delete(VendorController.BASE_URL + "/1"))
                .andExpect(status().isOk());

        //then
        then(vendorService).should().deleteVendorById(anyLong());
    }
}