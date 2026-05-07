package com.mkr.springappsecurity.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPersonRepository extends PersonRepository, JpaRepository<Person, Long> {
}
