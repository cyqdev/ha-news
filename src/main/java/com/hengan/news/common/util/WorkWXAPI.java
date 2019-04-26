package com.hengan.news.common.util;

/**
 * @Author: geekfly
 * @Description:  企业微信相关API及常量定义
 * @Date: 2017/10/12
 * @Modified By:
 */
public class WorkWXAPI {

    public static String CORPID = ""; //企业ID
    public static Integer AGENTID = 1000017; //应用ID
    public static String AUTH_APP_SECRET = ""; //应用1Secret
    public static String CONTACTS_SECRET = ""; //应用2Secret
    public static String TOKEN = ""; //API接收Token
    public static String EncodingAESKey = "";//API接收EncodingAESKey

    public static String GET_ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";
    public static String GET_USER_OPENID_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=%s&code=%s";
    public static String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";
    public static String GET_USER_INFO = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=%s&userid=%s";


    //标签相关
    public static String TAG_ADD_USERS_URL = "https://qyapi.weixin.qq.com/cgi-bin/tag/addtagusers?access_token=%s";
    public static String TAG_DELETE_USERS_URL = "https://qyapi.weixin.qq.com/cgi-bin/tag/deltagusers?access_token=%s";

    /*
       成员管理
     */
    //获取部门成员
    public static String CONTACTS_SIMPLE_LIST = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=%s&department_id=%s&fetch_child=%s";
    //获取部门成员详情
    public static String CONTACTS_LIST = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=%s&department_id=%s&fetch_child=%s";
    //更新成员
    public static String CONTACTS_UPDATE= "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=%s";
    //创建成员
    public static String CONTACTS_CREATE = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=%s";
    //读取成员
    public static String CONTACTS_GET = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=%s&userid=%s";


    /*
    部门管理
     */
    //创建部门
    public static String DEPARTMENT_CREATE = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=%s";
    //更新部门
    public static  String DEPARTMENT_UPDATE = "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=%s";
    //获取部门列表
    public static String DEPARTMENT_LIST = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=%s&id=%s";

    //js api
    public static String JS_API = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=%s";

    //认证通过
    public static String AUTH_SUCCESS = "https://qyapi.weixin.qq.com/cgi-bin/user/authsucc?access_token=%s&userid=%s";

    //不同身份人员所在部门ID
    public static Integer DEPARTMENT_JZG_ID_OTHER = 4658;    //教师(其他)
    public static Integer DEPARTMENT_JZG_ID = 2638;    //教师(新)

    public static Integer DEPARTMENT_NO_AUTH = 1889; //未认证用户部门
    public static Integer DEPARTMENT_ROOT = 1; //根部门ID

    //菜单ID
    public static String MENU_ID_USER_INFO = "user_info"; //个人信息
    public static String MENU_ID_USER_BIND = "user_bind"; //身份认证

}