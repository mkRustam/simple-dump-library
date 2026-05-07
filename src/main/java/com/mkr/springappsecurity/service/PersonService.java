package com.mkr.springappsecurity.service;

import com.mkr.springappsecurity.entity.model.Person;
import com.mkr.springappsecurity.repository.jpa.JpaPersonRepository;
import com.mkr.springappsecurity.web.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final JpaPersonRepository jpaPersonRepository;

}
