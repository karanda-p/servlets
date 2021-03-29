package com.karandap.servlets.dao;

import com.karandap.servlets.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    @Override
    @Transactional
    public Book getBookById(int id) {
        Book result;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        result = em.find(Book.class, id);
        em.getTransaction().commit();
        return result;
    }

    @Override
    @Transactional
    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("from Book");
        bookList = query.getResultList();
        em.getTransaction().commit();
        return bookList;
    }

    @Override
    @Transactional
    public void deleteBook(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Book book = getBookById(id);
        em.remove(book);
        em.getTransaction().commit();
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.getTransaction().commit();
    }
}
