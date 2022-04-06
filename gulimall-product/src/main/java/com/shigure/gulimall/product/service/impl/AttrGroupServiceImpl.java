package com.shigure.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.mysql.cj.util.StringUtils;
import com.shigure.common.constant.ProductConstant;
import com.shigure.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.shigure.gulimall.product.dao.AttrDao;
import com.shigure.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.shigure.gulimall.product.entity.AttrEntity;
import com.shigure.gulimall.product.service.AttrAttrgroupRelationService;
import com.shigure.gulimall.product.service.AttrService;
import com.shigure.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shigure.common.utils.PageUtils;
import com.shigure.common.utils.Query;

import com.shigure.gulimall.product.dao.AttrGroupDao;
import com.shigure.gulimall.product.entity.AttrGroupEntity;
import com.shigure.gulimall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Autowired
    AttrAttrgroupRelationDao relationDao;
    @Autowired
    AttrDao attrDao;
    @Autowired
    AttrGroupDao groupDao;
    @Autowired
    AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {

        if(catelogId==0){       //0是查询所有
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), new QueryWrapper<AttrGroupEntity>());
            return new PageUtils(page);
        }else {
            String key = (String) params.get("key");
            // select * from pms_attr_group where catelog_id=? and (attr_group_id=key or attr_group_name like %key%)  ,如果有key则要进行多字段模糊匹配
            QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>().eq("catelog_id",catelogId);
            if(!StringUtils.isNullOrEmpty(key)){
                wrapper.and((obj)->{
                    obj.eq("attr_group_id",key).or().like("attr_group_name",key);
                });

            }
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), wrapper);
            return new PageUtils(page);
        }

    }

    //获取当前分组没有关联的所有属性‘
    @Override
    public PageUtils queryNoRelation(Map<String, Object> params, Long attrgroupId) {
        //1.当前分组只能该关联自己所属分类里面的所有属性
        AttrGroupEntity groupEntity = groupDao.selectById(attrgroupId);
        Long catelogId = groupEntity.getCatelogId();
        //2.当前分组只能关联别的分组没有引用的属性

        //2.1找到当前分类下的其他分组以及这些分组关联的属性

        List<AttrGroupEntity> groups = groupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId)/*.ne("attr_group_id",attrgroupId)*/);

        List<Long> list = groups.stream().map(item -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());
        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", list));
        List<Long> collect = entities.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());


        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if(collect!=null||collect.size()>0){
            wrapper.notIn("attr_id",collect);
        }
        String key = (String) params.get("key");       //如果key存在，则需要模糊查询
        if(!StringUtils.isNullOrEmpty(key)){           //添加匹配模糊查询
            wrapper.and((w)->{
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> attrEntityIPage = attrDao.selectPage(new Query<AttrEntity>().getPage(params), wrapper);

        PageUtils pageUtils = new PageUtils(attrEntityIPage);
        return pageUtils;
    }

    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatId(Long catId) {
        //获取当前属性的所有分组
        List<AttrGroupEntity> entityList = groupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catId));
        //对于每个分组，获取所有属性
        List<AttrGroupWithAttrsVo> vos = entityList.stream().map(item -> {
            AttrGroupWithAttrsVo vo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(item, vo);
//            Long groupId = item.getAttrGroupId();     //分组id
//            List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", groupId));
//            List<Long> collect = entities.stream().map(it -> {
//                Long attrId = it.getAttrId();
//                return attrId;
//            }).collect(Collectors.toList());        //所有属性的id
//            List<AttrEntity> list = attrDao.selectList(new QueryWrapper<AttrEntity>().in("attr_id", collect));
            List<AttrEntity> list = attrService.getRelationAttr(vo.getAttrGroupId());
            vo.setAttrs(list);
            return vo;
        }).collect(Collectors.toList());


        return vos;
    }

//    @Override
//    public AttrEntity[] queryNoRelation(AttrGroupEntity attrGroup) {
//        //获取当前分组的所属分类
//        Long catelogId = attrGroup.getCatelogId();
//        //获取当前分类的所有属性
//        List<AttrEntity> attrs = attrDao.selectList(new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId));
//        //获取与当前分组关联的属性
//        List<AttrAttrgroupRelationEntity> relationEntities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroup.getAttrGroupId()));
//        r
//        return new AttrEntity[0];
//    }

}