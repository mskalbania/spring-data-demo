package edu.spring.data.entity.employee;

import edu.spring.data.entity.address.AddressSummary;

public interface EmployeeSummary {

    String getFirstName();

    String getLastName();

    AddressSummary getAddress();

    default String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
