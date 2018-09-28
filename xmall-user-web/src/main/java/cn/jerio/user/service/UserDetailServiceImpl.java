package cn.jerio.user.service;

import cn.jerio.pojo.TbUser;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
                return new User(username,user.getPassword(),grantAuths);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
}
