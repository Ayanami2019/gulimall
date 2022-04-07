package com.shigure.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shigure.common.to.SkuRedutionTo;
import com.shigure.common.utils.PageUtils;
import com.shigure.gulimall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author shigure
 * @email shigure_daisuki@163.com
 * @date 2022-03-26 09:26:49
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuRedutionTo skuRedutionTo);
}

