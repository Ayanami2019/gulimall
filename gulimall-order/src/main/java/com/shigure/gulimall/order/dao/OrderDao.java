package com.shigure.gulimall.order.dao;

import com.shigure.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author shigure
 * @email shigure_daisuki@163.com
 * @date 2022-03-26 11:02:38
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
