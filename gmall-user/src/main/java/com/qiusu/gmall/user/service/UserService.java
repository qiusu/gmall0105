package com.qiusu.gmall.user.service;

import com.qiusu.gmall.user.bean.UmsMember;
import com.qiusu.gmall.user.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}
