package edu.spring.data.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Address {

    private final String street;
    private final String city;

    public static Address fromEntity(edu.spring.data.entity.address.Address entity) {
        return new Address(entity.getStreet(), entity.getCity());
    }
}
