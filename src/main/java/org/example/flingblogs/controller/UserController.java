package org.example.flingblogs.controller;


import org.example.flingblogs.model.User;
import org.example.flingblogs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> request) {
        System.out.println("Hello Register");
        String username = request.get("username");
        String password = request.get("password");
        String email = request.get("email");

        User user = userService.register(username, password, email);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "注册成功");
        response.put("userId", user.getId());
        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        String token = userService.login(username, password);
        Map<String, Object> response = new HashMap<>();

        if (token != null) {
            response.put("message", "登录成功");
            response.put("token", token);
        }else {
            response.put("message", "用户名或密码错误");
        }
        return response;
    }

}
