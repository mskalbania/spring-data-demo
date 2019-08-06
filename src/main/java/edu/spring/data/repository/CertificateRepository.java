package edu.spring.data.repository;

import edu.spring.data.entity.Certificate;
import org.springframework.data.repository.CrudRepository;

public interface CertificateRepository extends CrudRepository<Certificate, Integer> {
}
