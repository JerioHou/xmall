package cn.jerio;

import cn.jerio.mapper.TbGoodsDescMapper;
import cn.jerio.mapper.TbGoodsMapper;
import cn.jerio.mapper.TbItemCatMapper;
import cn.jerio.mapper.TbItemMapper;
import cn.jerio.pojo.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XmallPageServiceApplicationTests {

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

	@Test
	public void contextLoads() {
        try {
//            Long goodsId = 149187842867912L;
            TbGoodsExample example = new TbGoodsExample();

            List<TbGoods> tbGoodses = goodsMapper.selectByExample(null);
            Template template = configuration.getTemplate("item.ftl");

            for (TbGoods goods : tbGoodses){
                System.out.println("商品id : "+goods.getId());
                Long goodsId = goods.getId();
                Map dataModel=new HashMap<>();
                //1.加载商品表数据
                TbGoods goods2 = goodsMapper.selectByPrimaryKey(goodsId);
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
                TbItemExample example2=new TbItemExample();
                TbItemExample.Criteria criteria = example2.createCriteria();
                criteria.andStatusEqualTo("1");//状态为有效
                criteria.andGoodsIdEqualTo(goodsId);//指定SPU ID
                example.setOrderByClause("is_default desc");//按照状态降序，保证第一个为默认
                List<TbItem> itemList = itemMapper.selectByExample(example2);
                dataModel.put("itemList", itemList);

                //String fileName = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/"+goodsId+".html";
                String fileName = this.getClass().getResource("/").toURI().getPath()+"/"+goodsId+".html";
                Writer out=new FileWriter(new File(fileName));
                template.process(dataModel, out);
                out.close();
            }

        }catch (Exception e){
            System.out.println("商品详情静态文件生成失败");
            e.printStackTrace();
        }
    }
}
