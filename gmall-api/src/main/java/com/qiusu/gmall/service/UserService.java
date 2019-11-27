package com.qiusu.gmall.service;


import com.qiusu.gmall.bean.UmsMember;
import com.qiusu.gmall.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}
