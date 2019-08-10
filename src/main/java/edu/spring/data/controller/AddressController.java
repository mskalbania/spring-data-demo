package edu.spring.data.controller;

import edu.spring.data.model.Address;
import edu.spring.data.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public ResponseEntity<List<Address>> getAddresses(@RequestParam(name = "pageSize", required = false) Optional<String> pageSize,
                                                      @RequestParam(name = "pageNumber", required = false) Optional<String> pageNumber) {
        try {
            if (!pageSize.isPresent() || !pageNumber.isPresent()) {
                return findAll();
            } else {
                return findAllPaged(pageSize.get(), pageNumber.get());
            }
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    private ResponseEntity<List<Address>> findAll() {
        return ResponseEntity.ok(addressService.findAll());
    }

    private ResponseEntity<List<Address>> findAllPaged(String pageSize, String pageNumber) {
        try {
            return ResponseEntity.ok(addressService.findAllPaged(parseInt(pageSize), parseInt(pageNumber)));
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(BAD_REQUEST, "Non integer parameter supplied.");
        }
    }
}
