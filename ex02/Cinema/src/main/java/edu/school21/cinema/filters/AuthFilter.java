package edu.school21.cinema.filters;

import edu.school21.cinema.models.User;
import edu.school21.cinema.services.UserService;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@WebFilter(value = {"/profile", "/images/*"})
public class AuthFilter implements Filter {
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ApplicationContext applicationContext = (ApplicationContext) filterConfig.getServletContext().getAttribute("springContext");
        userService = applicationContext.getBean(UserService.class);
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !userService.auth(user)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            String[] split = req.getRequestURI().split("/");
            int len = 3;
            if (split.length > 1 && split[1].equals("cinema")) {
                len = 4;
            }

            if (split.length == len) {
                if (!userService.authImages(user.getPhone(), UUID.fromString(split[len - 1]))) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
