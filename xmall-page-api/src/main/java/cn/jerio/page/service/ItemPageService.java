package cn.jerio.page.service;

/**
 * 商品详细页接口
 * Created by Jerio on 2018/9/26
 */
public interface ItemPageService {

    /**
     * 生成商品详细页
     * @param goodsId
     */
    public boolean genItemHtml(Long goodsId);
}
