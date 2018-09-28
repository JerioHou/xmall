package cn.jerio.user.service;

import cn.jerio.pojo.TbUser;
import cn.jerio.util.CookieUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jerio on 2018/09/28
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //构建角色列表
        List<GrantedAuthority> grantAuths= new ArrayList<>();
        grantAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        //得到用户对象
        TbUser user = userService.findByUsername(username);
        if(user!=null){
            if(user.getStatus().equals("1")){
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                HttpServletResponse response = attributes.getResponse();
                String token = UUID.randomUUID().toString();
                CookieUtil.setCookieAndDomain(request,response,"xmall-token",token,3600*24,"xmall.cn");
                userService.cacheUserinfo(user.getId(),token);
                return new User(username,user.getPassword(),grantAuths);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
}
