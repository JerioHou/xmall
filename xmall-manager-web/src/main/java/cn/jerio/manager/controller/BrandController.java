package cn.jerio.manager.controller;

import cn.jerio.pojo.TbBrand;
import cn.jerio.sellergoods.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Franky on 2018/09/17
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference(version = "1.0.0")
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll();
    }
}
