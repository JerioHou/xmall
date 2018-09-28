package cn.jerio.cart.controller;

import cn.jerio.cart.service.CartService;
import cn.jerio.entity.Result;
import cn.jerio.pojo.TbUser;
import cn.jerio.pojogroup.Cart;
import cn.jerio.user.service.UserService;
import cn.jerio.util.CookieUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Jerio on 2018/09/28
 */
@RestController
@RequestMapping("/cart")
@CrossOrigin(origins="http://www.page.xmall.cn:9105",allowCredentials="true")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Reference
    private CartService cartService;

    @Reference
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;


    @Value("${server.servlet.session.cookie.domain}")
    String domainName;

    /**
     * 购物车列表
     * @param request
     * @return
     */
    @RequestMapping("/findCartList")
    public List<Cart> findCartList(){
        TbUser user = getUserByToken();

        String cartListString = CookieUtil.getCookieValue(request, "cartList","UTF-8");
        if(cartListString==null || cartListString.equals("")){
            cartListString="[]";
        }
        List<Cart> cartList_cookie = JSON.parseArray(cartListString, Cart.class);
        if (user == null){
            return cartList_cookie;
        } else {
            List<Cart> cartList_redis = cartService.findCartListFromRedis(user.getUsername());//从redis中提取
            if (cartList_cookie.size() > 0) {//如果本地存在购物车
                //合并购物车
                cartList_redis = cartService.mergeCartList(cartList_redis, cartList_cookie);

            }
            return cartList_redis;
        }
    }

    /**
     * 添加商品到购物车
     * @param request
     * @param response
     * @param itemId
     * @param num
     * @return
     */
    @RequestMapping("/addGoodsToCartList")
    public Result addGoodsToCartList(Long itemId, Integer num){
        TbUser user = getUserByToken();
        try {
            List<Cart> cartList =findCartList();//获取购物车列表
            cartList = cartService.addGoodsToCartList(cartList, itemId, num);

            if (user == null){
                CookieUtil.setCookieAndDomain(request,response,"cartList",JSON.toJSONString(cartList),3600*24,domainName);
            }else {
                cartService.saveCartListToRedis(user.getUsername(), cartList);
            }
            return Result.success("添加成功");
        } catch (Exception e) {
            logger.error("购物车添加失败",e);
            return Result.fail("添加失败");
        }
    }


    private TbUser getUserByToken(){
        String token = CookieUtil.getCookieValue(request,"xmall-token");
        if (StringUtils.isBlank(token))
            return null;
        Long userId = userService.getCacheUserinfo(token);
        if (userId == null)
            return null;
        return userService.findOne(userId);
    }
}
