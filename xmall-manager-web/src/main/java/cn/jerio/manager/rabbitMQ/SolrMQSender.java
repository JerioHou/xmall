package cn.jerio.manager.rabbitMQ;

import cn.jerio.constant.Const;
import cn.jerio.pojo.TbItem;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Jerio on 2018/09/27
 */
@Component
public class SolrMQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    //更新索引
    public void sendSolrUpdateMessage(List<TbItem> itemList){
        System.out.println("发送消息");
        amqpTemplate.convertAndSend(Const.TOPIC_EXCHANGE,Const.ROUTING_KEY_SOLR_UPDATE,itemList);
    }

    //删除索引
    public void sendSolrDeleteMessage(Long[] ids){
        System.out.println("发送消息");
        amqpTemplate.convertAndSend(Const.TOPIC_EXCHANGE,Const.ROUTING_KEY_SOLR_DELETE,ids);
    }
}
