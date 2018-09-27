package cn.jerio.search.mq;

import cn.jerio.constant.Const;
import cn.jerio.pojo.TbItem;
import cn.jerio.search.service.ItemSearchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jerio on 2018/09/27
 */
@Component
public class SolrMQReciver {

    @Autowired
    private ItemSearchService itemSearchService;

    /**
     * 更新索引
     * @param itemList
     */
    @RabbitListener(queues = Const.TOPIC_QUEUE_SOLR_UPDATE)
    public void reciveSolrUpdate(List<TbItem> itemList){
        System.out.println("接受到更新索引的消息");
        itemSearchService.importList(itemList);
    }

    /**
     * 删除索引
     * @param itemList
     */
    @RabbitListener(queues = Const.TOPIC_QUEUE_SOLR_DELETE)
    public void reciveSolrDelete(Long[] ids){
        System.out.println("接受到删除索引的消息消息");
        itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
    }

}
