package com.shigure.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shigure.common.utils.PageUtils;
import com.shigure.gulimall.product.entity.SpuInfoDescEntity;
import com.shigure.gulimall.product.entity.SpuInfoEntity;
import com.shigure.gulimall.product.vo.SpuSaveVo;

import java.util.List;
import java.util.Map;

/**
 * spu信息
 *
 * @author shigure
 * @email shigure_daisuki@163.com
 * @date 2022-03-25 20:17:17
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
    void saveBaseSpuInfo(SpuInfoEntity infoEntity);

    void saveSpuInfo(SpuSaveVo vo);


    PageUtils queryPageBycondition(Map<String, Object> params);
}

