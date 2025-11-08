package com.tjsp.pericia.controller;

import java.util.Collection;

import org.springframework.stereotype.Service;

@Service
public interface BookService {
    Collection<Book> getBooks();
    Book addBook(Book book);
}