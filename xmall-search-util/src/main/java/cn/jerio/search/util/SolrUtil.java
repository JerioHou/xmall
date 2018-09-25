package cn.jerio.search.util;

import cn.jerio.mapper.TbItemMapper;
import cn.jerio.pojo.TbItem;
import cn.jerio.pojo.TbItemExample;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Jerio on 2018/09/25
 */
@Component
public class SolrUtil {

    @Autowired
    private TbItemMapper itemMapper;

    /**
     * 导入商品数据
     */
    public void importItemData(SolrTemplate solrTemplate){
        TbItemExample example=new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");//已审核
        List<TbItem> itemList = itemMapper.selectByExample(example);
        System.out.println("===商品列表===");
        for(TbItem item:itemList){
            Map specMap= JSON.parseObject(item.getSpec());//将spec字段中的json字符串转换为map
            item.setSpecMap(specMap);//给带注解的字段赋值
            System.out.println(item.getTitle());
        }
        solrTemplate.saveBeans("collection1",itemList);
        solrTemplate.commit("collection1");
        System.out.println("===结束===");
    }

}
