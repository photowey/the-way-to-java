package com.photowey.spring.in.action.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * {@code BeanNameController}
 *
 * @author photowey
 * @date 2021/11/12
 * @see * http://localhost:7923/bean/name/controller
 * @since 1.0.0
 */
@Slf4j
@Component("/bean/name/controller")
public class BeanNameController extends AbstractController {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("Say hello from spring-in-action bean-name controller, path:{}", "/bean/name/controller");
        ModelAndView modelAndView = new ModelAndView();
        PrintWriter writer = response.getWriter();

        writer.println(String.format("Say hello from spring-in-action bean-name controller,path:%s", "/bean/name/controller"));
        writer.flush();

        writer.close();

        return modelAndView;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView();
    }
}
