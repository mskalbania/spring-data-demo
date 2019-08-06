package edu.spring.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CERTIFICATE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Certificate {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private int id;

    @Column(name = "cert_id", unique = true, nullable = false)
    private String certificateId;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private Employee employee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return id == that.id &&
                Objects.equals(certificateId, that.certificateId) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, certificateId, description);
    }
}
