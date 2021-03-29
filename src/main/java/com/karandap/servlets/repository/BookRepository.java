package com.karandap.servlets.repository;

import com.karandap.servlets.entity.Book;

public interface BookRepository{

    public Book getBookById(int id);
}
