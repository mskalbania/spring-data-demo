package edu.spring.data.service;

import edu.spring.data.model.Address;
import edu.spring.data.repository.AddressRepository;
import edu.spring.data.utils.StreamUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public List<Address> findAll() {
        return StreamUtils.fromIterable(repository.findAll())
                          .map(Address::fromEntity)
                          .collect(Collectors.toList());
    }

    public List<Address> findAllPaged(int pageSize, int pageNumber) {
        return repository.findAll(PageRequest.of(pageNumber - 1, pageSize)) //Pages are indexed from 0
                         .stream()
                         .map(Address::fromEntity)
                         .collect(Collectors.toList());
    }
}
