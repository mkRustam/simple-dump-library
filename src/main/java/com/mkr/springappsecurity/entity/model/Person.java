package com.mkr.springappsecurity.entity.model;

import com.mkr.springappsecurity.entity.model.book.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persons")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "holder", fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    // @JoinTable - аннотация используется для настройки промежуточной таблицы,
    // которая связывает две сущности при отношениях ManyToMany
}
