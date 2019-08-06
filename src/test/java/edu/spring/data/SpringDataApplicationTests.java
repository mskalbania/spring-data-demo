package edu.spring.data;

import edu.spring.data.entity.Address;
import edu.spring.data.entity.Certificate;
import edu.spring.data.entity.Employee;
import edu.spring.data.entity.Position;
import edu.spring.data.repository.CertificateRepository;
import edu.spring.data.repository.EmployeeRepository;
import edu.spring.data.repository.PositionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.CollectionHelper.asSet;

@SpringBootTest
@RunWith(SpringRunner.class)
@SqlGroup({
                  @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql"),
                  @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:drop.sql")
          })
public class SpringDataApplicationTests {

    private static final Position DEVS = new Position(1, "DEVELOPERS", emptySet());
    private static final Position QA = new Position(3, "QA", emptySet());

    private static final Certificate CERT_OCA_1 = new Certificate(1, "aaa-bbb-ccc", "JAVA OCA", null);
    private static final Certificate CERT_OCP_1 = new Certificate(2, "ddd-eee-fff", "JAVA OCP", null);
    private static final Certificate CERT_OCA_2 = new Certificate(3, "ggg-hhh-iii", "JAVA OCA", null);

    private static final Address ADD_1 = new Address(1, "Ogrodowa", "Siewierz");

    private static final Employee EMP_1 = new Employee(1, "Mateusz", "Skalbania", ADD_1, asSet(CERT_OCP_1, CERT_OCA_1), asSet(DEVS, QA));
    private static final Employee EMP_2 = new Employee(2, "John", "Doe", ADD_1, asSet(CERT_OCA_2), asSet(DEVS));

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @Test
    public void employeeAllFieldsPopulateTest() {
        //when
        Employee retrieved = employeeRepository.findAll().iterator().next();

        //then
        assertThat(retrieved).isEqualTo(EMP_1);
    }

    @Test
    public void positionEmployeePopulateTest() {
        //when
        Iterator<Position> positions = positionRepository.findAll().iterator();

        //then
        Position devs = positions.next();
        assertThat(devs.getEmployees()).contains(EMP_1, EMP_2);

        Position devOps = positions.next();
        assertThat(devOps.getEmployees()).isEmpty();

        Position qa = positions.next();
        assertThat(qa.getEmployees()).contains(EMP_1);
    }

    @Test
    public void certificateEmployeePopulateTest() {
        //when
        Iterator<Certificate> certs = certificateRepository.findAll().iterator();

        //then
        Certificate firstOCA = certs.next();
        assertThat(firstOCA.getEmployee()).isEqualTo(EMP_1);

        Certificate firstOCP = certs.next();
        assertThat(firstOCP.getEmployee()).isEqualTo(EMP_1);

        Certificate secondOCA = certs.next();
        assertThat(secondOCA.getEmployee()).isEqualTo(EMP_2);
    }

    @Test
    public void employeeOCPQueryTest() {
        //when
        List<Employee> ocpEmployers = employeeRepository.findAllOCPCertificatedEmployers();

        //then
        assertThat(ocpEmployers).containsExactly(EMP_1);
    }

    private String positionText(Position position) {
        return "POS: " + position.getName() + " [" +

                position.getEmployees().stream()
                        .map(it -> it.getFistName() + " " + it.getLastName())
                        .collect(Collectors.joining(", "))

                + "]";
    }
}
