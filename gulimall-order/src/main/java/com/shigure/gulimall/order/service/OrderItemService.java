package com.shigure.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shigure.common.utils.PageUtils;
import com.shigure.gulimall.order.entity.OrderItemEntity;

import java.util.Map;

/**
 * 订单项信息
 *
 * @author shigure
 * @email shigure_daisuki@163.com
 * @date 2022-03-26 11:02:38
 */
public interface OrderItemService extends IService<OrderItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

