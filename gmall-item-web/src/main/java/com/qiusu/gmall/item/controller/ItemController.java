package com.qiusu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qiusu.gmall.bean.PmsProductSaleAttr;
import com.qiusu.gmall.bean.PmsSkuInfo;
import com.qiusu.gmall.service.SkuService;
import com.qiusu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    @Reference
    SkuService skuService;

    @Reference
    SpuService spuService;

    @RequestMapping("{skuId}.html")
    public String index(@PathVariable String skuId,ModelMap map) {

        PmsSkuInfo pmsSkuInfo = skuService.getSkubyId(skuId);
        map.put("skuInfo",pmsSkuInfo);

         List<PmsProductSaleAttr> pmsProductSaleAttrs= spuService.spuSaleAttrListCheckBySku(pmsSkuInfo.getProductId(),pmsSkuInfo.getId());

        map.put("spuSaleAttrListCheckBySku",pmsProductSaleAttrs);

        return "item";
    }
}
