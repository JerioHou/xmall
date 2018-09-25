package cn.jerio.search.service.impl;

import cn.jerio.pojo.TbItem;
import cn.jerio.search.service.ItemSearchService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jerio on 2018/09/25
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    
    private static final String COLLECTION = "collection1";
    

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public Map<String, Object> search(Map searchMap) {

        Map<String,Object> map = new HashMap<>();
        Query query = new SimpleQuery();

        Criteria criteria = new Criteria("item_keywords");
        criteria.is(searchMap.get("keywords"));
        query.addCriteria(criteria);
        ScoredPage<TbItem> tbItems = solrTemplate.queryForPage(COLLECTION, query, TbItem.class);
        map.put("rows", tbItems.getContent());
        return map;
    }
}
