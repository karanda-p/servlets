package com.karandap.servlets.dao;

import com.karandap.servlets.entity.Book;

import java.util.List;

public interface BookDAO {

    public Book getBookById(int id);

    public List<Book> getAllBooks();

    public void deleteBook(int id);

    public void updateBook(Book book);

}
