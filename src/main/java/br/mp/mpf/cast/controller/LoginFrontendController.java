package br.mp.mpf.cast.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginFrontendController {

    @Autowired private Environment env;

    @GetMapping("/login-frontend")
    public void loginFrontend(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestParam(required = false) String redirect) throws IOException {

        if (StringUtils.isNotBlank(redirect) && "DESENV".equals(env.getProperty("ambiente")))
            response.sendRedirect(redirect);
        else
            response.sendRedirect(request.getContextPath());
    }

}
