package com.qiusu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.qiusu.gmall.bean.PmsSkuAttrValue;
import com.qiusu.gmall.bean.PmsSkuImage;
import com.qiusu.gmall.bean.PmsSkuInfo;
import com.qiusu.gmall.bean.PmsSkuSaleAttrValue;
import com.qiusu.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.qiusu.gmall.manage.mapper.PmsSkuImageMapper;
import com.qiusu.gmall.manage.mapper.PmsSkuInfoMapper;
import com.qiusu.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.qiusu.gmall.service.SkuService;
import com.qiusu.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        // 插入skuInfo
        int i = pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();

        // 插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }

        // 插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }

        // 插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }


    }

    public PmsSkuInfo getSkuByIdFromDb(String skuId){
        Example example = new Example(PmsSkuInfo.class);
        example.createCriteria().andEqualTo("id",skuId);

        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectOneByExample(example);

        Example example1 = new Example(PmsSkuImage.class);
        example1.createCriteria().andEqualTo("skuId",skuId);
        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.selectByExample(example1);
        pmsSkuInfo.setSkuImageList(pmsSkuImages);
        return pmsSkuInfo;
    }

    @Override
    public PmsSkuInfo getSkubyId(String skuId) {
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        Jedis jedis =null;
        try {
            jedis = redisUtil.getJedis();

            String skuKey = "sku:"+skuId+":info";
            String skuLock = "sku:"+skuId+":lock";
            String skuJson = jedis.get(skuKey);
            if(StringUtils.isNotBlank(skuJson)){
                pmsSkuInfo = JSON.parseObject(skuJson,PmsSkuInfo.class);
            }else{
                String isOk = jedis.set(skuLock, "1", "nx", "px", 10);
                if(StringUtils.isNotBlank(isOk) && "OK".equals(isOk)){
                    pmsSkuInfo = getSkuByIdFromDb(skuId);
                    if(pmsSkuInfo!=null){
                        jedis.set(skuKey,JSON.toJSONString(pmsSkuInfo));
                    }else {
                        jedis.setex(skuKey,60*3,JSON.toJSONString(""));
                    }
                }else {
                    Thread.sleep(3000);
                    return getSkubyId(skuId);
                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }


        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);

        return pmsSkuInfos;
    }
}
