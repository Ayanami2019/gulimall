package com.shigure.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.shigure.gulimall.product.entity.ProductAttrValueEntity;
import com.shigure.gulimall.product.service.ProductAttrValueService;
import com.shigure.gulimall.product.vo.AttrRespVo;
import com.shigure.gulimall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.shigure.gulimall.product.entity.AttrEntity;
import com.shigure.gulimall.product.service.AttrService;
import com.shigure.common.utils.PageUtils;
import com.shigure.common.utils.R;



/**
 * 商品属性
 *
 * @author shigure
 * @email shigure_daisuki@163.com
 * @date 2022-03-25 21:00:58
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrlistforspu(@PathVariable("spuId") Long spuId){
        List<ProductAttrValueEntity> entityList = productAttrValueService.baseAttrlistforspu(spuId);

        return R.ok().put("data",null);
    }



    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String, Object> params,@PathVariable("catelogId") Long catelogId,@PathVariable("attrType") String type){
       PageUtils page = attrService.queryBaseAttrPage(params,catelogId,type);
        return R.ok().put("page",page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
		//AttrEntity attr = attrService.getById(attrId);
        AttrRespVo respVo = attrService.getAttrInfo(attrId);
        return R.ok().put("attr", respVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr){
		attrService.updateAttr(attr);

        return R.ok();
    }
    @PostMapping("/update/{spuId}")
    public R updateSpuAttr(@PathVariable("spuId") Long spuId,
                           @RequestBody List<ProductAttrValueEntity> entities){
        productAttrValueService.updateSpuAttr(spuId,entities);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
