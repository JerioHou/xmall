package cn.jerio.cart.util;

import cn.jerio.pojo.TbUser;
import cn.jerio.user.service.UserService;
import cn.jerio.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Jerio on 2018/09/29
 */
public class UserUtil {

    public static TbUser getUserByToken(HttpServletRequest request, UserService userService){
        String token = CookieUtil.getCookieValue(request,"xmall-token");
        if (StringUtils.isBlank(token))
            return null;
        Long userId = userService.getCacheUserinfo(token);
        if (userId == null)
            return null;
        return userService.findOne(userId);
    }
}
