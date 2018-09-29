package cn.jerio.order.config;

import cn.jerio.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Jerio on 2018/09/29
 */
@Configuration
public class OrderConfig {

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(0L,0L);
    }
}
