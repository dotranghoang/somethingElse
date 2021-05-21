package com.somethingwithjava.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.somethingwithjava.common.JwtProvider;
import com.somethingwithjava.common.RedisUtil;
import com.somethingwithjava.model.ResponseWithoutResult;
import com.somethingwithjava.model.User;
import com.somethingwithjava.repository.IUserRepository;
import com.somethingwithjava.service.IMPL.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class HttpInterceptors extends HandlerInterceptorAdapter {
    @Autowired
    IUserRepository userRepository;

    @Override
    //    before request execute by controller
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        final String authorizationHeaderValue = request.getHeader("Authorization");
        switch (request.getServletPath()) {
            case "/auth/signup/":
            case "/auth/login":
                return true;
            case "/user":
                return checkTokenInPreRequest(authorizationHeaderValue, response);
            default:
                responseWhenInvalidUrl(response);
                return false;
        }
    }

    @Override
    //    after request execute by controller
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) {

    }

    private boolean checkTokenInPreRequest(String authorizationHeaderValue, HttpServletResponse response) throws IOException {
        if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer")) {
            String token = authorizationHeaderValue.substring(7);
            boolean rsCheck = JwtProvider.validateToken(token);
            if (rsCheck) {
                String userName = JwtProvider.getUserIdFromJwt(token);
                boolean isExistUser = RedisUtil.isExistValue(userName);
                if (isExistUser)
                    return true;
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        ResponseWithoutResult responseWithoutResult =
                new ResponseWithoutResult(HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.UNAUTHORIZED.getReasonPhrase(),"Invalid token!");
        response.getWriter().write(mapper.writeValueAsString(responseWithoutResult));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(responseWithoutResult.getHttpCode());
        return false;
    }

    private void responseWhenInvalidUrl(HttpServletResponse response) throws IOException {
        response.getWriter().write("<head>\n" +
                "\t<meta content=\"text/html;charset=utf-8\" http-equiv=\"Content-Type\">\n" +
                "\t<meta content=\"utf-8\" http-equiv=\"encoding\">\n" +
                "\t<title>Error</title>\n" +
                "\t<style>\n" +
                "\t\tbody {\n" +
                "\t\t\twidth: 35em;\n" +
                "\t\t\tmargin: 0 auto;\n" +
                "\t\t\tfont-family: Tahoma, Verdana, Arial, sans-serif;\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\t<h1>An error occurred.</h1>\n" +
                "\t<p>Sorry, the page you are looking for is currently unavailable.<br/>\n" +
                "Please try again later.</p>\n" +
                "</body>");
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }
}
