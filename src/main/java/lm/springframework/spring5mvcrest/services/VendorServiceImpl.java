package lm.springframework.spring5mvcrest.services;

import lm.springframework.spring5mvcrest.api.v1.mappers.VendorMapper;
import lm.springframework.spring5mvcrest.api.v1.model.VendorDTO;
import lm.springframework.spring5mvcrest.controllers.VendorController;
import lm.springframework.spring5mvcrest.controllers.exceptions.ResourceNotFoundException;
import lm.springframework.spring5mvcrest.domain.Vendor;
import lm.springframework.spring5mvcrest.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService{

    private VendorRepository vendorRepository;

    private VendorMapper vendorMapper;

    @Autowired
    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream().map(
                vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setUrl(VendorController.BASE_URL+"/"+vendorDTO.getId());
                    return vendorDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long vendorId) {
        return vendorRepository.findById(vendorId).map(
                vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setUrl(VendorController.BASE_URL+"/"+vendorDTO.getId());
                    return vendorDTO;
                }).orElseThrow(() -> new ResourceNotFoundException("Vendor with id="+vendorId+" is not found"));
    }

    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorRepository.save(vendorMapper.vendorDtoToVendor(vendorDTO));
        VendorDTO savedVendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        savedVendorDTO.setUrl(VendorController.BASE_URL+"/"+savedVendorDTO.getId());
        return savedVendorDTO;
    }

    @Override
    public VendorDTO updateVendor(Long vendorId, VendorDTO inVendorDTO) {
        Optional<Vendor> vendorOpt = vendorRepository.findById(vendorId);
        if (!vendorOpt.isPresent()){
            throw new ResourceNotFoundException("Vendor with id="+vendorId+" is not found");
        }
        inVendorDTO.setId(vendorOpt.get().getId());
        Vendor updatedVendor = vendorRepository.save(vendorMapper.vendorDtoToVendor(inVendorDTO));
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(updatedVendor);
        vendorDTO.setUrl(VendorController.BASE_URL+"/"+vendorDTO.getId());
        return vendorDTO;
    }

    @Override
    public VendorDTO patchVendor(Long vendorId, VendorDTO inVendorDTO) {
        Optional<Vendor> vendorOpt = vendorRepository.findById(vendorId);
        if (!vendorOpt.isPresent()){
            throw new ResourceNotFoundException("Vendor with id="+vendorId+" is not found");
        }
        Vendor vendorDb = vendorOpt.get();
        if (inVendorDTO.getName() != null){
            vendorDb.setName(inVendorDTO.getName());
        }
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendorDb));
        vendorDTO.setUrl(VendorController.BASE_URL+"/"+vendorDTO.getId());
        return vendorDTO;
    }

    @Override
    public void deleteVendorById(Long vendorId) {
        Optional<Vendor> vendorOpt = vendorRepository.findById(vendorId);
        if (!vendorOpt.isPresent()){
            throw new ResourceNotFoundException("Vendor with id="+vendorId+" is not found");
        }
        vendorRepository.deleteById(vendorId);
    }
}
