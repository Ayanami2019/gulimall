package com.shigure.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shigure.common.utils.PageUtils;
import com.shigure.gulimall.coupon.entity.HomeSubjectEntity;

import java.util.Map;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 *
 * @author shigure
 * @email shigure_daisuki@163.com
 * @date 2022-03-26 09:26:50
 */
public interface HomeSubjectService extends IService<HomeSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

