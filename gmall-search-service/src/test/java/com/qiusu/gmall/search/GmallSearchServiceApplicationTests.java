package com.qiusu.gmall.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qiusu.gmall.bean.PmsSkuInfo;
import com.qiusu.gmall.service.SkuService;
import io.searchbox.client.JestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallSearchServiceApplicationTests {

    @Reference
    SkuService skuService;

    @Autowired
    JestClient jestClient;

    @Test
    public void contextLoads() {
        List<PmsSkuInfo> allSku = skuService.getAllSku();

        ArrayList<PmsSkuInfo> list = new ArrayList<>();
        for (PmsSkuInfo pmsSkuInfo : allSku) {
            PmsSkuInfo pmsSkuInfo1 = new PmsSkuInfo();

            BeanUtils.copyProperties(pmsSkuInfo,pmsSkuInfo1);
            list.add(pmsSkuInfo1);
        }
        System.out.println(list);
    }

}
