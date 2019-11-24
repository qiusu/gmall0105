package com.qiusu.gmall.user.service.impl;

import com.qiusu.gmall.user.bean.UmsMember;
import com.qiusu.gmall.user.bean.UmsMemberReceiveAddress;
import com.qiusu.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.qiusu.gmall.user.mapper.UserMapper;
import com.qiusu.gmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
