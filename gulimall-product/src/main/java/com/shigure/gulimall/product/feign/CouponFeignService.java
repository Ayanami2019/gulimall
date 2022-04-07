package com.shigure.gulimall.product.feign;

import com.shigure.common.to.SkuRedutionTo;
import com.shigure.common.to.SpuBoundTo;
import com.shigure.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@FeignClient("gulimall-coupon")
public interface CouponFeignService {
    //1.CouponFeignService.saveSpuBounds(spuBoundTo)
//    1.1 @Requestbody将这个对象转为json
//    1.2 在注册中心中找到服务（gulimall-coupoin)，并给/coupon/spubounds/save发送请求
    // 1.3对方服务收到请求，请求体里有json数据。@Requestbody将json转为SpuBoundsEntity spuBounds
    //总之，只要json数据模型兼容，双方无需使用同一个to
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);


    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuRedutionTo skuRedutionTo);
}
