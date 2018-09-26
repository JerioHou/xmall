package cn.jerio;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XmallSellergoodsServiceApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;

	@Test
	public void contextLoads() {
        redisTemplate.boundHashOps("testList").put("testKey","testValue");
        redisTemplate.boundHashOps("testList2").put("testKey","testValue2");
        String str = (String) redisTemplate.boundHashOps("testList2").get("testKey");
        System.out.println(str);
    }

}
