package cn.jerio.portal.controller;

import cn.jerio.content.service.ContentService;
import cn.jerio.pojo.TbContent;
import cn.jerio.util.CookieUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Jerio on 2018/9/24
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    /**
     * 根据广告分类ID查询广告列表
     * @param categoryId
     * @return
     */
    @RequestMapping("/findByCategoryId")
    public List<TbContent> findByCategoryId(Long categoryId) {
        return contentService.findByCategoryId(categoryId);
    }

    @RequestMapping("/token")
    public String test(HttpServletRequest request, HttpServletResponse response){
        return CookieUtil.getCookieValue(request,"xmall-token");
    }
}
