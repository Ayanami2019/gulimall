package com.shigure.gulimall.product.dao;

import com.shigure.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author shigure
 * @email shigure_daisuki@163.com
 * @date 2022-03-25 20:17:17
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
