package cn.jerio.manager.controller;



import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jerio on 2018/09/19
 */
@Controller
public class IndexController {

    @RequestMapping("/login/name")
    @ResponseBody
    public Map name(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map map=new HashMap();
        map.put("loginName", name);
        return map ;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/admin/index")
    public String admin(){
        return "admin/index";
    }
}
