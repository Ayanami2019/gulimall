package com.shigure.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.shigure.gulimall.product.entity.AttrEntity;
import com.shigure.gulimall.product.service.AttrAttrgroupRelationService;
import com.shigure.gulimall.product.service.AttrService;
import com.shigure.gulimall.product.service.CategoryService;
import com.shigure.gulimall.product.vo.AttrGroupRelationVo;
import com.shigure.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.shigure.gulimall.product.entity.AttrGroupEntity;
import com.shigure.gulimall.product.service.AttrGroupService;
import com.shigure.common.utils.PageUtils;
import com.shigure.common.utils.R;



/**
 * 属性分组
 *
 * @author shigure
 * @email shigure_daisuki@163.com
 * @date 2022-03-25 21:00:58
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    AttrService attrService;
    @Autowired
    AttrAttrgroupRelationService relationService;

    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId){
        List<AttrEntity> entities =attrService.getRelationAttr(attrgroupId);
        return R.ok().put("data",entities);
    }
    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params,@PathVariable("catelogId") Long catelogId){
        //PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params,catelogId);

        return R.ok().put("page", page);
    }

    //获取分类下所有分组以及关联属性

    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catId){
        //1.查出当前分类下的所有属性分组

        //2.查出每个属性分组的所有属性
        List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithAttrsByCatId(catId);
        return R.ok().put("data",vos);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.findCatelogPath(catelogId);
        attrGroup.setCatelogPath(path);

        return R.ok().put("attrGroup", attrGroup);
    }

    //新建关联
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos){

        relationService.saveBatch(vos);
        return R.ok();
    }



    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos){
        attrService.deleteRelation(vos);
        return R.ok();
    }

    //获取属性分组没有关联的其他属性
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R getNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
                           @RequestParam Map<String,Object> params){
        PageUtils page = attrGroupService.queryNoRelation(params,attrgroupId);
        return R.ok().put("page",page);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
