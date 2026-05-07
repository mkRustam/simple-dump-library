package com.mkr.springappsecurity.person;

import java.util.Optional;

public interface PersonRepository {
    Optional<Person> findById(Long id);
    Person save(Person person);
}
