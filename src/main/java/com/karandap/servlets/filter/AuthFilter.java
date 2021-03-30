package com.karandap.servlets.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

@WebFilter("/books/*")
public class AuthFilter implements Filter {

    private static final String USER = "user";
    private static final String PASSWORD = "password";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String authorization = req.getHeader("Authorization");
        if (authorization == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String formattedAuth = authorization.substring(6);
        String authDecoded = new String(Base64.getDecoder().decode(formattedAuth));
        String[] auth = authDecoded.split(":");
        System.out.println(Arrays.toString(auth));
        if (auth[0].equals(USER) && auth[1].equals(PASSWORD)) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void destroy() {

    }
}
