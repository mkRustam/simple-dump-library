package com.mkr.springappsecurity.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonDto {

    private Long id;

    private String name;

    public static PersonDto toDto(Person person) {
        return new PersonDto(person.getId(), person.getName());
    }
}
