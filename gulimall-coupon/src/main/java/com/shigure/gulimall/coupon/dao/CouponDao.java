package com.shigure.gulimall.coupon.dao;

import com.shigure.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author shigure
 * @email shigure_daisuki@163.com
 * @date 2022-03-26 09:26:50
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
