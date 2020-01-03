package com.qiusu.gmall.search.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.qiusu.gmall.bean.PmsBaseAttrInfo;
import com.qiusu.gmall.bean.PmsSearchParam;
import com.qiusu.gmall.bean.PmsSearchSkuInfo;
import com.qiusu.gmall.bean.PmsSkuAttrValue;
import com.qiusu.gmall.service.AttrService;
import com.qiusu.gmall.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;

@Controller
public class SearchController {

    @Reference
    SearchService searchService;

    @Reference
    AttrService attrService;


    @RequestMapping("index")
    public String list(PmsSearchParam pmsSearchParam, ModelMap modelMap){


        List<PmsSearchSkuInfo> pmsSearchSkuInfoList =  searchService.list(pmsSearchParam);

        modelMap.put("skuLsInfoList",pmsSearchSkuInfoList);

        HashSet<String> valueIdSet = new HashSet<>();

        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfoList) {
            List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                valueIdSet.add(pmsSkuAttrValue.getValueId());
            }

        }

        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.getAttrValueListByValueId(valueIdSet);
        modelMap.put("attrList", pmsBaseAttrInfos);

        return "list";
    }

}
