package cn.jerio.sellergoods.service;

import cn.jerio.entity.PageResult;
import cn.jerio.pojo.TbBrand;

import java.util.List;

/**
 * Created by Jerio on 2018/09/17
 */
public interface BrandService {

    List<TbBrand> findAll();

    /**
     * 返回分页列表
     * @return
     */
    PageResult findPage(int pageNum, int pageSize);

    /**
     * 增加品牌
     */
    void add(TbBrand brand);

    /**
     * 修改
     */
    void update(TbBrand brand);
    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    TbBrand findOne(Long id);

    /**
     * 批量删除
     * @param ids
     */
    void delete(Long [] ids);

    /**
     * 分页
     * @param pageNum 当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    PageResult findPage(TbBrand brand, int pageNum,int pageSize);
}
