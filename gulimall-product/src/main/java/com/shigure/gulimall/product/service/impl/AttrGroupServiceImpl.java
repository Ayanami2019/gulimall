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

        if(catelogId==0){       //0???????????????
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params), new QueryWrapper<AttrGroupEntity>());
            return new PageUtils(page);
        }else {
            String key = (String) params.get("key");
            // select * from pms_attr_group where catelog_id=? and (attr_group_id=key or attr_group_name like %key%)  ,?????????key?????????????????????????????????
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

    //????????????????????????????????????????????????
    @Override
    public PageUtils queryNoRelation(Map<String, Object> params, Long attrgroupId) {
        //1.??????????????????????????????????????????????????????????????????
        AttrGroupEntity groupEntity = groupDao.selectById(attrgroupId);
        Long catelogId = groupEntity.getCatelogId();
        //2.?????????????????????????????????????????????????????????

        //2.1?????????????????????????????????????????????????????????????????????

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
        String key = (String) params.get("key");       //??????key??????????????????????????????
        if(!StringUtils.isNullOrEmpty(key)){           //????????????????????????
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
        //?????????????????????????????????
        List<AttrGroupEntity> entityList = groupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catId));
        //???????????????????????????????????????
        List<AttrGroupWithAttrsVo> vos = entityList.stream().map(item -> {
            AttrGroupWithAttrsVo vo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(item, vo);
//            Long groupId = item.getAttrGroupId();     //??????id
//            List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", groupId));
//            List<Long> collect = entities.stream().map(it -> {
//                Long attrId = it.getAttrId();
//                return attrId;
//            }).collect(Collectors.toList());        //???????????????id
//            List<AttrEntity> list = attrDao.selectList(new QueryWrapper<AttrEntity>().in("attr_id", collect));
            List<AttrEntity> list = attrService.getRelationAttr(vo.getAttrGroupId());
            vo.setAttrs(list);
            return vo;
        }).collect(Collectors.toList());


        return vos;
    }

//    @Override
//    public AttrEntity[] queryNoRelation(AttrGroupEntity attrGroup) {
//        //?????????????????????????????????
//        Long catelogId = attrGroup.getCatelogId();
//        //?????????????????????????????????
//        List<AttrEntity> attrs = attrDao.selectList(new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId));
//        //????????????????????????????????????
//        List<AttrAttrgroupRelationEntity> relationEntities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroup.getAttrGroupId()));
//        r
//        return new AttrEntity[0];
//    }

}