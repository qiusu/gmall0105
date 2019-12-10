package com.qiusu.gmall.manage.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.qiusu.gmall.bean.PmsProductInfo;
import com.qiusu.gmall.manage.util.PmsUploadUtil;
import com.qiusu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        spuService.saveSpuInfo(pmsProductInfo);
        return null;
    }

    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file")MultipartFile multipartFile){
        String s = PmsUploadUtil.uploadImage(multipartFile);
        System.out.println(s);
        return null;
    }

}
