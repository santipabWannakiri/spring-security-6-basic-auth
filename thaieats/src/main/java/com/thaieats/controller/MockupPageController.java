package com.thaieats.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MockupPageController {

    @GetMapping("/a")
    @ResponseBody
    public String anyone() {

        return "anyone can access ";
    }


    @GetMapping("/admin")
    @ResponseBody
    public String admin() {

        return "This can access by admin only";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user() {

        return "This can access by user or admin ";
    }

    @GetMapping("/super")
    @ResponseBody
    public String superUser() {

        return "This can access by superUser and admin";
    }

    @GetMapping("/403")
    @ResponseBody
    public String accessDenied() {

        return "errors/403";
    }

}
