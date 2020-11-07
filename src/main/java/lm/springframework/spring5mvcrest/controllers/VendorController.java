package lm.springframework.spring5mvcrest.controllers;

import lm.springframework.spring5mvcrest.api.v1.model.VendorDTO;
import lm.springframework.spring5mvcrest.api.v1.model.VendorListDTO;
import lm.springframework.spring5mvcrest.services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors";

    private VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public ResponseEntity<VendorListDTO> getAllVendors(){
        return new ResponseEntity<>(
                new VendorListDTO(vendorService.getAllVendors()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable Long id){
        return new ResponseEntity<>(vendorService.getVendorById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VendorDTO> createNewVendor(@RequestBody VendorDTO vendorDTO){
        return new ResponseEntity<>(vendorService.createVendor(vendorDTO),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorDTO> updateVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return new ResponseEntity<>(vendorService.updateVendor(id,vendorDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VendorDTO> patchVendor(@PathVariable Long id, @RequestBody VendorDTO vendorDTO){
        return new ResponseEntity<>(vendorService.patchVendor(id,vendorDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendorById(@PathVariable Long id){
        vendorService.deleteVendorById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
