package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import edu.school21.cinema.services.UserService;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "signIn", value = "/signin")
public class SignIn extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        ApplicationContext applicationCOntext = (ApplicationContext) context.getAttribute("springContext");
        this.userService = applicationCOntext.getBean(UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/jsp/signIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        edu.school21.cinema.models.SignIn signIn = new edu.school21.cinema.models.SignIn();
        signIn.setIp(req.getRemoteAddr());
        signIn.setPhoneUser(phone);
        signIn.setDate(new Timestamp(System.currentTimeMillis()));

        User findUser = userService.signIn(phone, password, signIn);

        if (findUser == null) {
            resp.sendRedirect("signin");
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("user", findUser);
            resp.sendRedirect("profile");
        }
    }
}
