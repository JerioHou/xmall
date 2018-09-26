package cn.jerio.page.service.impl;

import cn.jerio.mapper.TbGoodsDescMapper;
import cn.jerio.mapper.TbGoodsMapper;
import cn.jerio.mapper.TbItemCatMapper;
import cn.jerio.mapper.TbItemMapper;
import cn.jerio.page.service.ItemPageService;
import cn.jerio.pojo.TbGoods;
import cn.jerio.pojo.TbGoodsDesc;
import cn.jerio.pojo.TbItem;
import cn.jerio.pojo.TbItemExample;
import com.alibaba.dubbo.config.annotation.Service;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Jerio on 2018/9/26
 */
@Service
public class ItemPageServiceImpl  implements ItemPageService {

    private static final Logger logger = LoggerFactory.getLogger(ItemPageServiceImpl.class);

    @Value("${pagedir}")
    private String pagedir;
    @Autowired
    private Configuration configuration;
    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public boolean genItemHtml(Long goodsId) {

        try {
            Template template = configuration.getTemplate("item.ftl");

            Map dataModel=new HashMap<>();
            //1.加载商品表数据
            TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goods", goods);
            //2.加载商品扩展表数据
            TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goodsDesc", goodsDesc);
            //3.商品分类
            String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
            dataModel.put("itemCat1", itemCat1);
            dataModel.put("itemCat2", itemCat2);
            dataModel.put("itemCat3", itemCat3);
            //4.SKU列表
            TbItemExample example=new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andStatusEqualTo("1");//状态为有效
            criteria.andGoodsIdEqualTo(goodsId);//指定SPU ID
            example.setOrderByClause("is_default desc");//按照状态降序，保证第一个为默认
            List<TbItem> itemList = itemMapper.selectByExample(example);
            dataModel.put("itemList", itemList);

            String fileName = this.getClass().getResource("/").toURI().getPath()+pagedir+goodsId+".html";
            Writer out=new FileWriter(new File(fileName));
            template.process(dataModel, out);
            out.close();
            return true;
        }catch (Exception e){
            logger.error("商品详情静态文件生成失败",e);
        }
        return false;
    }
}
