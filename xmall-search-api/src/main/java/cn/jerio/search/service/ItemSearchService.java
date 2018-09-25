package cn.jerio.search.service;

import java.util.Map;

/**
 * Created by Jerio on 2018/09/25
 */
public interface ItemSearchService {

    /**
     * 搜索
     * @param searchMap
     * @return
     */
    public Map<String,Object> search(Map searchMap);
}
