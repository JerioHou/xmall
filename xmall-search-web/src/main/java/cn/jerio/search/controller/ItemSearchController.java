package cn.jerio.search.controller;

import cn.jerio.search.service.ItemSearchService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Jerio on 2018/09/25
 */
@RestController
@RequestMapping("/itemsearch")
public class ItemSearchController {

        @Reference
        private ItemSearchService itemSearchService;

        @PostMapping("/search")
        public Map<String, Object> search(@RequestBody Map searchMap ){
            return  itemSearchService.search(searchMap);
        }
    }
