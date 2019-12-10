package com.qiusu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qiusu.gmall.bean.PmsBaseAttrInfo;
import com.qiusu.gmall.bean.PmsBaseAttrValue;
import com.qiusu.gmall.bean.PmsBaseSaleAttr;
import com.qiusu.gmall.manage.mapper.PmsBaseAttrInfoMapper;
import com.qiusu.gmall.manage.mapper.PmsBaseAttrValueMapper;
import com.qiusu.gmall.manage.mapper.PmsBaseSaleAttrMapper;
import com.qiusu.gmall.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AttrServiceimpl implements AttrService {

    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;

    @Autowired
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;

    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        Example example = new Example(PmsBaseAttrInfo.class);
        example.createCriteria().andEqualTo("catalog3Id",catalog3Id);
        return pmsBaseAttrInfoMapper.selectByExample(example);
    }

    @Override
    public String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {

        if(StringUtils.isBlank(pmsBaseAttrInfo.getId())){
            //为空则保存

            //保存属性
            pmsBaseAttrInfoMapper.insertSelective(pmsBaseAttrInfo);

            //保存属性值
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList){
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
            }
        }else{
            //不为空则更新

            //修改属性
            Example example = new Example(PmsBaseAttrInfo.class);
            example.createCriteria().andEqualTo("id",pmsBaseAttrInfo.getId());
            pmsBaseAttrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo,example);

            //修改属性值
            Example ex = new Example(PmsBaseAttrValue.class);
            ex.createCriteria().andEqualTo("attrId",pmsBaseAttrInfo.getId());
            pmsBaseAttrValueMapper.deleteByExample(ex);

            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValueMapper.insertSelective(pmsBaseAttrValue);
            }
        }



        return null;
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        Example example = new Example(PmsBaseAttrValue.class);
        example.createCriteria().andEqualTo("attrId",attrId);
        return pmsBaseAttrValueMapper.selectByExample(example);
    }

    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectAll();
    }
}
