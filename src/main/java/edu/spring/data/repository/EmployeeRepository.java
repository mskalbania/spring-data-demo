package edu.spring.data.repository;

import edu.spring.data.entity.employee.Employee;
import edu.spring.data.entity.employee.EmployeeSummary;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    @Query("SELECT E FROM Employee E INNER JOIN Certificate C ON E.id = C.employee.id WHERE C.description='JAVA OCP'")
    List<Employee> findAllOCPCertificatedEmployers();

    //Auto
    List<Employee> findAllByAddress_City(String city);

    //JPQL
    @Query("SELECT E FROM Employee E INNER JOIN Address A ON E.address.id = A.id WHERE A.city = :city")
    List<Employee> findAllByCityJPQL(@Param("city") String city);

    //NATIVE
    @Query(value = "SELECT * FROM EMPLOYEE INNER JOIN ADDRESS A ON address_id = A.id WHERE A.city = :city", nativeQuery = true)
    List<Employee> findAllByCityNative(@Param("city") String city);

    //GRAPHS
    @EntityGraph(value = "Emploee.certs", type = LOAD)
    Optional<Employee> findByLastName(String lastName);

    @EntityGraph(attributePaths = {"certificates"})
    Optional<Employee> findByFirstName(String firstName);

    //PROJECTIONS
    List<EmployeeSummary> findByFirstNameAndLastName(String firstName, String lastName);
}