package com.mkr.springappsecurity.repository;

import com.mkr.springappsecurity.entity.model.Person;

import java.util.Optional;

public interface PersonRepository {
    Optional<Person> findById(Long id);
    Person save(Person person);
}
