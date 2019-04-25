package com.hengan.news.web;

import com.hengan.news.core.Result;
import com.hengan.news.model.po.UserPO;
import com.hengan.news.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Cyq
 * @Date 2019/4/24 15:08
 **/
@RestController
//@RequestMapping("/common/redpack")
@Api(value = "UserController", description="用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查找用户", notes = "查找用户")
    @GetMapping(value = "/me", produces = {"application/json;charset=UTF-8"})
    public UserPO getAll(){
        UserPO byPrimaryKey = userService.findByWorkCode("17105223");
        return byPrimaryKey;
    }

    @ApiOperation(value = "企业微信用户登录", notes = "企业微信用户登录")
    @GetMapping(value = "/wxlongin", produces = {"application/json;charset=UTF-8"})
    public Result wxLogin(@RequestParam(name = "code") String code){
//        UserPO byPrimaryKey = userService.wxLogin(code);
        userService.findByWorkCode("17105223");
        Result result = new Result();
        result.setData("");
        return result;
    }

}
