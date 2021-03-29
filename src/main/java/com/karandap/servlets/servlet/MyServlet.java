package com.karandap.servlets.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karandap.servlets.dao.BookDAO;
import com.karandap.servlets.dao.BookDAOImpl;
import com.karandap.servlets.entity.Book;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/books/*")
public class MyServlet extends HttpServlet {

    private BookDAO bookDAO;

    private ObjectMapper om;

    public void init() throws ServletException {
        bookDAO = new BookDAOImpl();
        om = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();
        String path = req.getPathInfo();
        List<Book> books = bookDAO.getAllBooks();
        if (path == null || path.equals("/")) {
            pw.print(om.writeValueAsString(books));
            return;
        }

        String[] splits = path.split("/");

        if (splits.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (bookDAO.getBookById(Integer.parseInt(splits[1])) == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        pw.print(om.writeValueAsString(bookDAO.getBookById(Integer.parseInt(splits[1]))));
        pw.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            Book book = om.readValue(req.getReader(), Book.class);
            bookDAO.updateBook(book);
            pw.print(om.writeValueAsString(book));

        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        List<Book> books = bookDAO.getAllBooks();
        if (path == null || path.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] splits = path.split("/");

        if (splits.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (bookDAO.getBookById(Integer.parseInt(splits[1])) == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        bookDAO.deleteBook(Integer.parseInt(splits[1]));

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter pw = resp.getWriter();
        String path = req.getPathInfo();
        List<Book> books = bookDAO.getAllBooks();
        if (path == null || path.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] splits = path.split("/");

        if (splits.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (bookDAO.getBookById(Integer.parseInt(splits[1])) == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Book book = om.readValue(req.getReader(), Book.class);
        book.setId(Integer.parseInt(splits[1]));
        bookDAO.updateBook(book);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        pw.print(om.writeValueAsString(book));

    }
}
