package cn.jerio.pojogroup;

import cn.jerio.pojo.TbOrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jerio on 2018/09/28
 */
@Data
public class Cart implements Serializable {

    private String sellerId;//商家ID
    private String sellerName;//商家名称
    private List<TbOrderItem> orderItemList;//购物车明细
}
