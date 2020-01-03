package com.qiusu.gmall.service;

import com.qiusu.gmall.bean.PmsSearchParam;
import com.qiusu.gmall.bean.PmsSearchSkuInfo;

import java.util.List;

public interface SearchService {


    List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam);
}
