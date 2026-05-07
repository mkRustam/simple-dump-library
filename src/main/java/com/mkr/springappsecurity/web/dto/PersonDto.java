package com.mkr.springappsecurity.web.dto;

import com.mkr.springappsecurity.entity.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
