package com.shigure.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shigure.common.utils.PageUtils;
import com.shigure.gulimall.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author shigure
 * @email shigure_daisuki@163.com
 * @date 2022-03-25 20:17:17
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttr(List<ProductAttrValueEntity> collect);


    List<ProductAttrValueEntity> baseAttrlistforspu(Long spuId);


    void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> entities);
}

