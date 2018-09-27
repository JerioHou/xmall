package cn.jerio;

import cn.jerio.mapper.TbGoodsDescMapper;
import cn.jerio.mapper.TbGoodsMapper;
import cn.jerio.pojo.TbGoods;
import cn.jerio.pojo.TbGoodsDesc;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
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

	@Test
	public void contextLoads() {
        try {
            Long goodsId = 149187842867912L;
            Template template = configuration.getTemplate("item.ftl");

            Map dataModel=new HashMap<>();
            //1.加载商品表数据
            TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goods", goods);
            //2.加载商品扩展表数据
            TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goodsDesc", goodsDesc);
            //String fileName = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"/"+goodsId+".html";
            String fileName = this.getClass().getResource("/").toURI().getPath()+"static/test.html";
            Writer out=new FileWriter(new File(fileName));
            template.process(dataModel, out);
            out.close();
        }catch (Exception e){
            System.out.println("商品详情静态文件生成失败");
            e.printStackTrace();
        }
    }
}
