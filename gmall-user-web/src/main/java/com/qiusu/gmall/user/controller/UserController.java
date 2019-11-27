package com.qiusu.gmall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qiusu.gmall.bean.UmsMember;
import com.qiusu.gmall.bean.UmsMemberReceiveAddress;
import com.qiusu.gmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Reference
    UserService userService;

    @RequestMapping("getReceiveAddressByMemberId")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId){
        List< UmsMemberReceiveAddress>  umsMemberReceiveAddress = userService.getReceiveAddressByMemberId(memberId);
        return umsMemberReceiveAddress;
    }

    @RequestMapping("getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUser(){
        List<UmsMember> userMembers = userService.getAllUser();
        return userMembers;
    }

    @RequestMapping("index")
    @ResponseBody
    public String index(){
        return "hello user";
    }
}
