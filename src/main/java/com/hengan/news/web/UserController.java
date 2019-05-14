package com.hengan.news.web;

import com.hengan.news.core.Result;
import com.hengan.news.core.ResultCode;
import com.hengan.news.model.po.UserPO;
import com.hengan.news.model.vo.UserVO;
import com.hengan.news.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Cyq
 * @Date 2019/4/24 15:08
 **/
@RestController
@Api(value = "UserController", description="用户接口")
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    UserDAO userDAO;

    @ApiOperation(value = "查找用户", notes = "查找用户")
    @GetMapping(value = "/me", produces = {"application/json;charset=UTF-8"})
    public UserPO getAll(){
        UserPO byPrimaryKey = userService.findByWorkCode("17105223");
        return byPrimaryKey;
    }

    @ApiOperation(value = "企业微信用户登录", notes = "企业微信用户登录")
    @GetMapping(value = "/wxlogin")
//    @ApiImplicitParam(name="code",value="个人code",required=true,dataType="String",paramType="query")
    public Result<UserVO> wxLogin(@RequestParam(name = "code") String code){
        System.out.println("开始登陆code:"+code);
        UserVO userVO = userService.wxLogin(code);
        Result<UserVO> result = new Result<>();
        result.setCode(ResultCode.SUCCESS);
        result.setData(userVO);
        return result;
    }

}
