package view.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static view.Commands.*;

public abstract class BasicServlet extends HttpServlet {

    public static final String UNKNOWN_EXCEPTION_MSG = "Unknown exception raised.";
    public static final String DB_EXCEPTION_MSG     = "Database exception raised.";
    public static final String ILLEGAL_ACCESS_MSG   = "Illegal access.";

    protected void errorResponse(HttpServletRequest request,
                                      HttpServletResponse response,
                                      String msg,
                                      String redirect) throws ServletException, IOException {

        request.setAttribute(ERR_RESPONSE_ARG, msg);
        request.getRequestDispatcher(redirect).forward(request, response);
    }

    protected void successResponse(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String msg,
                                 String redirect) throws ServletException, IOException {

        request.setAttribute(SUCCESS_RESPONSE_ARG, msg);
        request.getRequestDispatcher(redirect).forward(request, response);
    }

    protected void gotoPage(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newPage = request.getParameter(REDIRECT_ARG);
        request.getRequestDispatcher(newPage).forward(request, response);
    }

}
