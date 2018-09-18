package cn.jerio.manager.controller;

import cn.jerio.constant.Const;
import cn.jerio.entity.PageResult;
import cn.jerio.entity.Result;
import cn.jerio.pojo.TbBrand;
import cn.jerio.sellergoods.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Jerio on 2018/09/17
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

    @Reference(version = Const.XMALL_SELLERGOODS_SERVICE_VERSION)
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll();
    }

    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows){
        return brandService.findPage(page, rows);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody TbBrand brand){
        try {
            brandService.add(brand);
            return Result.success();
        } catch (Exception e) {
            logger.error("品牌添加失败",e);
            return Result.fail();
        }
    }

    /**
     * 修改
     * @param brand
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbBrand brand){
        try {
            brandService.update(brand);
            return Result.success("修改成功");
        } catch (Exception e) {
            logger.error("品牌修改失败",e);
            return Result.fail("修改失败");
        }
    }
    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbBrand findOne(Long id){
        return brandService.findOne(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long [] ids){
        try {
            brandService.delete(ids);
            return Result.success("删除成功");
        } catch (Exception e) {
            logger.error("品牌删除失败",e);
            return Result.fail("删除失败");
        }
    }

    /**
     * 查询+分页
     * @param brand
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbBrand brand, int page, int rows  ){
        return brandService.findPage(brand, page, rows);
    }
}
