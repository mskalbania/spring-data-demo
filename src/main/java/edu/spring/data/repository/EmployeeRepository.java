package edu.spring.data.repository;

import edu.spring.data.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    @Query("SELECT E FROM Employee E INNER JOIN Certificate C ON E.id = C.employee.id WHERE C.description='JAVA OCP'")
    List<Employee> findAllOCPCertificatedEmployers();
}