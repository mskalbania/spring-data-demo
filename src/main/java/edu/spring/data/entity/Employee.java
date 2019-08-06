package edu.spring.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "EMPLOYEE")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String fistName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToOne(optional = false, orphanRemoval = true)
    private Address address;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Set<Certificate> certificates;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "EMPLOYEE_POSITION",
               joinColumns = @JoinColumn(name = "employee_id", nullable = false, updatable = false),
               inverseJoinColumns = @JoinColumn(name = "position_id", nullable = false, updatable = false))
    private Set<Position> positions;
}
