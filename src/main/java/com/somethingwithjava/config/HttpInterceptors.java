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
            Object handler) {
        try {
            switch (request.getServletPath()) {
//                auth case
                case "/auth/signup":
                case "/auth/login":
                    return true;
//                    need check token case
                case "/user":
                    return checkTokenInPreRequest(request, response);
//                    error at convert to model or something else
                case "/error":
                    responseWhenGetBadRequest(response);
                    return false;
//                    case not correct router
                default:
                    responseWhenInvalidUrl(response);
                    return false;
            }
        } catch (Exception e) {
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

    private boolean checkTokenInPreRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authorizationHeaderValue = request.getHeader("Authorization");
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
                        HttpStatus.UNAUTHORIZED.getReasonPhrase(), "Invalid token!");
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
                "*{\n" +
                "    transition: all 0.6s;\n" +
                "}\n" +
                "\n" +
                "html {\n" +
                "    height: 100%;\n" +
                "}\n" +
                "\n" +
                "body{\n" +
                "    position: fixed;\n" +
                "    top: 20%;\n" +
                "    left: 50%;\n" +
                "    margin-top: -100px;\n" +
                "    margin-left: -200px;" +
                "}\n" +
                "\n" +
                "#main{\n" +
                "    display: table;\n" +
                "    width: 100%;\n" +
                "    height: 100vh;\n" +
                "    text-align: center;\n" +
                "}\n" +
                "\n" +
                ".fof{\n" +
                "\t  display: table-cell;\n" +
                "\t  vertical-align: middle;\n" +
                "}\n" +
                "\n" +
                ".fof h1{\n" +
                "\t  font-size: 50px;\n" +
                "\t  display: inline-block;\n" +
                "\t  padding-right: 12px;\n" +
                "\t  animation: type .5s alternate infinite;\n" +
                "}\n" +
                "\n" +
                "@keyframes type{\n" +
                "\t  from{box-shadow: inset -3px 0px 0px #888;}\n" +
                "\t  to{box-shadow: inset -3px 0px 0px transparent;}\n" +
                "}" +
                "\t</style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    \t<div class=\"fof\">\n" +
                "        \t\t<h1>Error 404</h1>\n" +
                "\t<p><h2>An error occurred.</h2></p>\n" +
                "\t<p>Sorry, the page you are looking for is currently unavailable.<br/>\n" +
                "Please try again later.</p>\n" +
                "    \t</div>" +
                "</body>");
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    private void responseWhenGetBadRequest(HttpServletResponse res) throws IOException {
        res.getWriter().write("<head>\n" +
                "\t<meta content=\"text/html;charset=utf-8\" http-equiv=\"Content-Type\">\n" +
                "\t<meta content=\"utf-8\" http-equiv=\"encoding\">\n" +
                "\t<title>Error</title>\n" +
                "\t<style>\n" +
                "*{\n" +
                "    transition: all 0.6s;\n" +
                "}\n" +
                "\n" +
                "html {\n" +
                "    height: 100%;\n" +
                "}\n" +
                "\n" +
                "body{\n" +
                "    position: fixed;\n" +
                "    top: 20%;\n" +
                "    left: 50%;\n" +
                "    margin-top: -100px;\n" +
                "    margin-left: -200px;" +
                "}\n" +
                "\n" +
                "#main{\n" +
                "    display: table;\n" +
                "    width: 100%;\n" +
                "    height: 100vh;\n" +
                "    text-align: center;\n" +
                "}\n" +
                "\n" +
                ".fof{\n" +
                "\t  display: table-cell;\n" +
                "\t  vertical-align: middle;\n" +
                "}\n" +
                "\n" +
                ".fof h1{\n" +
                "\t  font-size: 50px;\n" +
                "\t  display: inline-block;\n" +
                "\t  padding-right: 12px;\n" +
                "\t  animation: type .5s alternate infinite;\n" +
                "}\n" +
                "\n" +
                "@keyframes type{\n" +
                "\t  from{box-shadow: inset -3px 0px 0px #888;}\n" +
                "\t  to{box-shadow: inset -3px 0px 0px transparent;}\n" +
                "}" +
                "\t</style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    \t<div class=\"fof\">\n" +
                "        \t\t<h1>Error 400</h1>\n" +
                "\t<p><h2>Bad Request!</h2></p>\n" +
                "\t<p><h2>An error occurred.</h2></p>\n" +
                "\t<p>Maybe your request have some issue. Please check and try again later.<br/>\n" +
                "    \t</div>" +
                "</body>");
        res.setStatus(HttpStatus.BAD_REQUEST.value());
    }
}
