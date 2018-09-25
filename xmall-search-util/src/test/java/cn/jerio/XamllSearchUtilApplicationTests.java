package cn.jerio;

import cn.jerio.search.util.SolrUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XamllSearchUtilApplicationTests {

	@Autowired
	private SolrTemplate solrTemplate;

	@Autowired
	SolrUtil solrUtil;
	@Test
	public void contextLoads() {

		solrUtil.importItemData(solrTemplate);
	}

}
