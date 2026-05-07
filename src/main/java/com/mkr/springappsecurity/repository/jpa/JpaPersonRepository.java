package com.mkr.springappsecurity.repository.jpa;

import com.mkr.springappsecurity.entity.model.Person;
import com.mkr.springappsecurity.repository.PersonRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPersonRepository extends PersonRepository, JpaRepository<Person, Long> {

}
