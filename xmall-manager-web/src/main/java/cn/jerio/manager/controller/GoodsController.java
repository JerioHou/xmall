package cn.jerio.manager.controller;

import cn.jerio.entity.PageResult;
import cn.jerio.entity.Result;
import cn.jerio.page.service.ItemPageService;
import cn.jerio.pojo.TbGoods;
import cn.jerio.pojo.TbItem;
import cn.jerio.pojogroup.Goods;
import cn.jerio.search.service.ItemSearchService;
import cn.jerio.sellergoods.service.GoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

	@Reference
	private GoodsService goodsService;

    @Reference
    private ItemSearchService itemSearchService;

    @Reference
    private ItemPageService itemPageService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return goodsService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Goods goods){
		try {
			goodsService.add(goods);
			return Result.success("增加成功");
		} catch (Exception e) {
			logger.error("失败",e);
			return Result.fail("增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Goods goods){
		try {
			goodsService.update(goods);
			return Result.success("修改成功");
		} catch (Exception e) {
			logger.error("失败",e);
			return Result.fail("修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			goodsService.delete(ids);
            itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
			return Result.success("删除成功");
		} catch (Exception e) {
			logger.error("失败",e);
			return Result.fail("删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param goods
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){
		return goodsService.findPage(goods, page, rows);		
	}

    /**
     * 更新状态
     * @param ids
     * @param status
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status){
        try {
            goodsService.updateStatus(ids, status);
            if(status.equals("1")){//审核通过
                List<TbItem> itemList = goodsService.findItemListByGoodsIdandStatus(ids, status);
                //调用搜索接口实现数据批量导入
                if(itemList.size()>0){
                    itemSearchService.importList(itemList);
                }else{
                    System.out.println("没有明细数据");
                }
                //生成静态页面
                for (Long goodId : ids){
                    itemPageService.genItemHtml(goodId);
                }

            }
            return Result.success("成功");
        } catch (Exception e) {
            logger.error("修改成功",e);
            return Result.fail("失败");
        }
    }
	
}
