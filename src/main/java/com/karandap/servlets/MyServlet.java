package com.karandap.servlets;


import com.karandap.servlets.dao.BookDAOImpl;
import com.karandap.servlets.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/book")
public class MyServlet extends HttpServlet {

    private BookDAOImpl bookDAO = new BookDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        PrintWriter pw = resp.getWriter();
        if (id == null) {
            pw.print(bookDAO.getAllBooks());
        } else {
            Book result = bookDAO.getBookById(Integer.valueOf(id));
            pw.print(result);
        }

        pw.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Book book = new Book();
        resp.setCharacterEncoding("UTF-8");
        book.setName(req.getParameter("name"));
        book.setAuthor(req.getParameter("author"));
        bookDAO.updateBook(book);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        bookDAO.deleteBook(Integer.valueOf(req.getParameter("id")));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Book book = new Book();
        resp.setCharacterEncoding("UTF-8");
        book.setId(Integer.valueOf(req.getParameter("id")));
        book.setName(req.getParameter("name"));
        book.setAuthor(req.getParameter("author"));
        bookDAO.updateBook(book);
    }
}
