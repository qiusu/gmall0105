package com.qiusu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qiusu.gmall.bean.PmsProductInfo;
import com.qiusu.gmall.manage.mapper.PmsProductInfoMapper;
import com.qiusu.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    PmsProductInfoMapper pmsProductInfoMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {

        Example example = new Example(PmsProductInfo.class);
        example.createCriteria().andEqualTo("catalog3Id",catalog3Id);
        return pmsProductInfoMapper.selectByExample(example);
    }
}
