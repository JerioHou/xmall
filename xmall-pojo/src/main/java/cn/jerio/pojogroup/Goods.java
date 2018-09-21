package cn.jerio.pojogroup;

import cn.jerio.pojo.TbGoods;
import cn.jerio.pojo.TbGoodsDesc;
import cn.jerio.pojo.TbItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品DTO
 * Created by Jerio on 2018/09/21
 */
@Data
public class Goods implements Serializable {
    private TbGoods goods;//商品SPU
    private TbGoodsDesc goodsDesc;//商品扩展
    private List<TbItem> itemList;//商品SKU列表
}
