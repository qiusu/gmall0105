package com.qiusu.gmall.service;

import com.qiusu.gmall.bean.PmsBaseAttrInfo;
import com.qiusu.gmall.bean.PmsBaseAttrValue;
import com.qiusu.gmall.bean.PmsBaseSaleAttr;

import java.util.HashSet;
import java.util.List;


public interface AttrService {

    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> baseSaleAttrList();

    List<PmsBaseAttrInfo> getAttrValueListByValueId(HashSet<String> valueIdSet);
}
