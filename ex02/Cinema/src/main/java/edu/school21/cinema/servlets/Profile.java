package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import edu.school21.cinema.services.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "profile", value = "/profile")
public class Profile extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        ApplicationContext applicationCOntext = (ApplicationContext) context.getAttribute("springContext");
        this.userService = applicationCOntext.getBean(UserService.class);
    }

    @Override
    @Transactional
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (resp.getStatus() != HttpServletResponse.SC_FORBIDDEN) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            req.setAttribute("list_sign_in", userService.getListSignInDTO(user.getPhone()));
            req.setAttribute("list_images", userService.getListImages(user.getPhone()));

            req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
        }
    }
}
