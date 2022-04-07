package com.shigure.gulimall.ware.dao;

import com.shigure.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author shigure
 * @email shigure_daisuki@163.com
 * @date 2022-03-26 11:13:59
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    void addStock(Long skuId, Long wareId, Integer skuNum);

}
