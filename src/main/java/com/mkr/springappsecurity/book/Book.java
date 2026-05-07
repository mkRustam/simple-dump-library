package com.mkr.springappsecurity.book;

import com.mkr.springappsecurity.person.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person holder;
}
