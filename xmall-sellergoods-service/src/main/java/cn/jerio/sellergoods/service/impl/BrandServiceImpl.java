package cn.jerio.sellergoods.service.impl;

import cn.jerio.mapper.TbBrandMapper;
import cn.jerio.pojo.TbBrand;
import cn.jerio.sellergoods.service.BrandService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Franky on 2018/09/17
 */

@Service(version = "1.0.0")
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TbBrandMapper brandMapper;

    @Override
    public List<TbBrand> findAll() {
        return brandMapper.selectByExample(null);
    }
}
