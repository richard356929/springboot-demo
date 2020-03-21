package com.richard.springboot.demo.controller;

import com.richard.springboot.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zcy
 * @create 2020-03-20 20:06:54
 */

@Controller
@RequestMapping(value = "/test")
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/test")
    @ResponseBody
    public void testController(@RequestParam(value = "id") int id) {
        testService.testService();
        System.out.println("id=" + id);
    }
}
