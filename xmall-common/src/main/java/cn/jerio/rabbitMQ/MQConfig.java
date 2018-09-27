package cn.jerio.rabbitMQ;

import cn.jerio.constant.Const;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




/**
 * Created by Jerio on 2018/09/27
 */
@Configuration
public class MQConfig {

    @Bean
    public Queue solrUpdateQueue(){
        return new Queue(Const.TOPIC_QUEUE_SOLR_UPDATE) ;
    }
    @Bean
    public Queue solrDeleteQueue(){
        return new Queue(Const.TOPIC_QUEUE_SOLR_UPDATE) ;
    }

    @Bean
    public Queue pageQueue(){
        return new Queue(Const.TOPIC_QUEUE_PAGE) ;
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(Const.TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBandingSolr(){
        return BindingBuilder.bind(solrUpdateQueue()).to(topicExchange()).with(Const.ROUTING_KEY_SOLR_UPDATE);
    }
    @Bean
    public Binding topicBandingSolrDelete(){
        return BindingBuilder.bind(solrDeleteQueue()).to(topicExchange()).with(Const.ROUTING_KEY_SOLR_DELETE);
    }
    @Bean
    public Binding topicBandingPage(){
        return BindingBuilder.bind(pageQueue()).to(topicExchange()).with(Const.ROUTING_KEY_PAGE);
    }
}
