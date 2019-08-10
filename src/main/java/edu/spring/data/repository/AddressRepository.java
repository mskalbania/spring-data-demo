package edu.spring.data.repository;

import edu.spring.data.entity.address.Address;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressRepository extends PagingAndSortingRepository<Address, Integer> {
}
