package com.qiusu.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qiusu.gmall.bean.UmsMember;
import com.qiusu.gmall.bean.UmsMemberReceiveAddress;
import com.qiusu.gmall.service.UserService;
import com.qiusu.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.qiusu.gmall.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Override
    public List<UmsMember> getAllUser() {
        //return userMapper.selectAllUser();
        return userMapper.selectAll();
    }

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        /*UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberId);
        return umsMemberReceiveAddressMapper.select(umsMemberReceiveAddress);*/

        Example example = new Example(UmsMemberReceiveAddress.class);
        example.createCriteria().andEqualTo("memberId","1");
        return umsMemberReceiveAddressMapper.selectByExample(example);
    }
}
