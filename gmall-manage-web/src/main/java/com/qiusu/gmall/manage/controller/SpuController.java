package com.qiusu.gmall.manage.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.qiusu.gmall.bean.PmsProductInfo;
import com.qiusu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class SpuController {


    @Reference
    SpuService spuService;

    //查询spu
    @RequestMapping("spuList")
    @ResponseBody
    public List<PmsProductInfo> spuList(String catalog3Id){

        return spuService.spuList(catalog3Id);
    }

}
