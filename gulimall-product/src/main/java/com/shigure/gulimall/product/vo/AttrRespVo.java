package com.shigure.gulimall.product.vo;

import lombok.Data;

@Data
public class AttrRespVo extends AttrVo{
    //分类名
    private String catelogName;
    //分组名
    private String groupName;
    private Long[] catelogPath;

}
