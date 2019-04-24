package com.hengan.news.web;

import com.hengan.news.model.po.UserPO;
import com.hengan.news.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Cyq
 * @Date 2019/4/24 15:08
 **/
@RestController
//@RequestMapping("/common/redpack")
@Api(value = "UserController", description="用户接口")
public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @ApiOperation(value = "查找用户", notes = "查找用户")
//    @GetMapping(value = "/me", produces = {"application/json;charset=UTF-8"})
//    public UserPO getAll(){
//        return userService.findById(Long.valueOf("17105223"));
//    }

}
