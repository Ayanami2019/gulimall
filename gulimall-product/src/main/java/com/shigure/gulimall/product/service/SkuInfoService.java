package com.shigure.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shigure.common.utils.PageUtils;
import com.shigure.gulimall.product.entity.SkuInfoEntity;
import com.shigure.gulimall.product.entity.SpuInfoEntity;
import com.shigure.gulimall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * sku信息
 *
 * @author shigure
 * @email shigure_daisuki@163.com
 * @date 2022-03-25 20:17:17
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);


    void saveSkuInfo(SkuInfoEntity skuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

