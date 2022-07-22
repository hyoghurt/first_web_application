package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import edu.school21.cinema.services.UserService;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "images", value = "/images/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
public class Images extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        ApplicationContext applicationCOntext = (ApplicationContext) context.getAttribute("springContext");
        this.userService = applicationCOntext.getBean(UserService.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();

            Part part = req.getPart("file");
            User user = (User) session.getAttribute("user");

            userService.saveFile(part, user.getPhone());

        } catch (IllegalStateException e) {
        } finally {
            resp.sendRedirect("profile");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] split = req.getRequestURI().split("/");
        int len = 3;
        if (split.length > 1 && split[1].equals("cinema")) {
            len = 4;
        }

        if (split.length != len) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            byte[] imageById = userService.getImageById(split[len - 1]);

            if (imageById == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                ServletOutputStream outputStream = resp.getOutputStream();
                resp.setContentType("image/jpeg");
                outputStream.write(imageById);
            }
        }
    }
}
