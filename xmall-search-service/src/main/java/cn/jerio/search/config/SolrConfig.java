package cn.jerio.search.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * Created by Jerio on 2018/09/27
 */
@Configuration
public class SolrConfig {
    @Value("${spring.data.solr.host}")
    private String host;


    @Bean
    public SolrTemplate solrTemplate(){
        return new SolrTemplate(solrClient());
    }

    @Bean
    public SolrClient solrClient(){
        return new HttpSolrClient.Builder(host).build();
    }

}
