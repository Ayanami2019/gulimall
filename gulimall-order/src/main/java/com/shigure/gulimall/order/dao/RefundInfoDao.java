package com.shigure.gulimall.order.dao;

import com.shigure.gulimall.order.entity.RefundInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退款信息
 * 
 * @author shigure
 * @email shigure_daisuki@163.com
 * @date 2022-03-26 11:02:38
 */
@Mapper
public interface RefundInfoDao extends BaseMapper<RefundInfoEntity> {
	
}
