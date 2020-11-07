package lm.springframework.spring5mvcrest.services;

import lm.springframework.spring5mvcrest.api.v1.model.VendorDTO;
import lm.springframework.spring5mvcrest.domain.Vendor;

import java.util.List;

public interface VendorService {
    List<VendorDTO> getAllVendors();

    VendorDTO getVendorById(Long vendorId);

    VendorDTO createVendor(VendorDTO vendorDTO);

    VendorDTO updateVendor(Long vendorId, VendorDTO vendorDTO);

    VendorDTO patchVendor(Long vendorId, VendorDTO vendorDTO);

    void deleteVendorById(Long vendorId);
}
