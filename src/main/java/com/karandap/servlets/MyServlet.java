package com.karandap.servlets;

import com.google.gson.Gson;
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

    private EntityManagerFactory emf;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        em.getTransaction().begin();
        Book result = em.find(Book.class, Integer.valueOf(id));

        Gson gson = new Gson();
        String bookJSON = gson.toJson(result);
        PrintWriter pw = resp.getWriter();

        pw.print(bookJSON);
        em.getTransaction().commit();
        pw.flush();
        em.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        Book book = new Book();
        resp.setCharacterEncoding("UTF-8");
        book.setName(req.getParameter("name"));
        book.setAuthor(req.getParameter("author"));
        em.getTransaction().begin();
        em.merge(book);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Book target = em.find(Book.class, Integer.valueOf(req.getParameter("id")));
        em.remove(target);
        em.getTransaction().commit();
        em.close();
    }
}
