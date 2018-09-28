package cn.jerio.user.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jerio on 2018/09/28
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/name")
    @ResponseBody
    public Map showName(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map map=new HashMap();
        map.put("loginName", name);
        return map;
    }

    @GetMapping()
    public String login(){
        return "login.html";
    }
}
