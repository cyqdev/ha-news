package com.hengan.news.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hengan.news.model.vo.NewsMsgVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class QYWXUtil {

    //HTTP(S) 连接超时时间，单位毫秒
    public static final int connectTimeoutMs = 6*1000;
    //HTTP(S) 读数据超时时间，单位毫秒
    public static final int readTimeoutMs = 8*1000;

    public static String getAccessToken(String corpid, String corpsecret){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=#id&corpsecret=#secrect";
        url = url.replace("#id", corpid);
        url = url.replace("#secrect", corpsecret);
        String accessToken = (String)JSON.parseObject(GetWeixinHttpRequestByte.get(url)).get("access_token");
        return accessToken;
    }

    public static String getJsApiTicket(String accessToken){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=#access_token";
        url = url.replace("#access_token", accessToken);
        String ticket = (String)JSON.parseObject(GetWeixinHttpRequestByte.get(url)).get("ticket");
        return ticket;
    }

    public static JSONObject getUserInfo(String code, String accessToken){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=#access_token&code=#code";
        url = url.replace("#code",code);
        url = url.replace("#access_token", accessToken);
        JSONObject obj = JSON.parseObject(GetWeixinHttpRequestByte.get(url));
        return obj;
    }

    public static JSONObject getUserDetail(String accessToken, String userTicket){
        String detailUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail?access_token=#access_token";
        detailUrl = detailUrl.replace("#access_token", accessToken);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("user_ticket",userTicket);
        String detailResult = GetWeixinHttpRequestByte.post(detailUrl, JSON.toJSONString(param));
        JSONObject detailObj = JSON.parseObject(detailResult);
        return detailObj;
    }

    /**
     * 发送文本消息
     * @param corpid
     * @param redirecturi
     * @param agentid
     * @param accessToken
     * @param workCodes
     * @param title
     * @param content
     * @param state
     */
    public static void pushTextMessage(String corpid, String redirecturi, String agentid, String accessToken, String workCodes, String title,String content,String state){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=#access_token";
        url = url.replace("#access_token", accessToken);
        String touser = workCodes;
//        for (String workCode:workCodes) {
//            touser += workCode + "|";
//        }
//        touser = touser.substring(0,touser.length()-1);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("touser", touser);
        param.put("msgtype", "textcard");
        param.put("agentid", agentid);
        Map<String,String> textCardMap = new HashMap<String, String>();
        textCardMap.put("title",title);
        String dateStr = DateTimeUtils.getFormatDate(new Date(), DateTimeUtils.PART_DATE_FORMAT_CN);
        textCardMap.put("description","<div class='gray'>"+dateStr+"</div>");
        textCardMap.put("btntxt","点击查看");
        String jumpUrl = getRedirectUrl(corpid,redirecturi,agentid,state);

        textCardMap.put("url",jumpUrl);
        param.put("textcard",textCardMap);
        GetWeixinHttpRequestByte.post(url, JSON.toJSONString(param));
    }

    /**
     * 发送多条图文消息
     * @param accessToken
     * @param touser 工号 | 分隔
     * @param toparty 部门 | 分隔
     * @param totag 标签 | 分隔
     * @param agentid
     * @param mpnewsVOS
     */
    public static void sendnNewsMessage(String accessToken, String touser,String toparty,String totag, Integer agentid, List<NewsMsgVO> mpnewsVOS){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=#access_token";
        url = url.replace("#access_token", accessToken);

        //组装消息
        JSONObject json = new JSONObject();
        if(StringUtils.isNotBlank(touser)){
            json.put("touser", touser);
        }
        if(StringUtils.isNotBlank(toparty)){
            json.put("toparty", toparty);
        }
        if(StringUtils.isNotBlank(totag)){
            json.put("totag", totag);
        }
        json.put("msgtype", "news");
        json.put("agentid", agentid);
        JSONObject mpnewsJson = new JSONObject();
        JSONArray array= JSONArray.parseArray(JSON.toJSONString(mpnewsVOS));
        mpnewsJson.put("articles",array);
        json.put("news",mpnewsJson);
//        json.put("safe",0);
        System.out.println(JSON.toJSONString(json));
        GetWeixinHttpRequestByte.post(url, JSON.toJSONString(json));
    }

    public static void pushSimpleTextMessage(String agentid,String accessToken,String workCodes,String content){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=#access_token";
        url = url.replace("#access_token", accessToken);
        String touser = workCodes;
//        for (String workCode:workCodes) {
//            touser += workCode + "|";
//        }
//        touser = touser.substring(0,touser.length()-1);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("touser", touser);
        param.put("msgtype", "text");
        param.put("agentid", agentid);
        param.put("content", content);

        GetWeixinHttpRequestByte.post(url, JSON.toJSONString(param));
    }

    public static String getRedirectUrl(String corpid, String redirecturi, String agentid, String state){
        String appid="appid="+ corpid;
        String redirectUri = "redirect_uri=" + redirecturi;
        String responseType = "response_type=code";
        String scope = "scope=snsapi_userinfo";
        agentid = "agentid="+ agentid;
        StringBuffer jumpUrl = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?");
        jumpUrl.append(appid);
        jumpUrl.append("&"+redirectUri);
        jumpUrl.append("&"+responseType);
        jumpUrl.append("&"+scope);
        if (StringUtils.isNotBlank(state)){
            jumpUrl.append("&"+state);
        }
        jumpUrl.append("&"+agentid);
        jumpUrl.append("#wechat_redirect");

        return jumpUrl.toString();
    }

    /**
     * 获得部门列表
     * @param accessToken
     * @return
     */
    public static JSONArray getDepartments(String accessToken, String id){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=#access_token&id=#id";
        url = url.replace("#access_token", accessToken);
        url = url.replace("#id", id);
        String result = GetWeixinHttpRequestByte.get(url);
        JSONObject obj = JSONObject.parseObject(result);
        JSONArray arr = obj.getJSONArray("department");

        return arr;
    }

    /**
     * 获得人员列表
     * @param accessToken
     * @return
     */
    public static JSONArray getUsers(String accessToken, String departmentId){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=#access_token&department_id=#department_id&fetch_child=1";
        url = url.replace("#access_token", accessToken);
        url = url.replace("#department_id", departmentId);
        String result = GetWeixinHttpRequestByte.get(url);
        JSONObject obj = JSONObject.parseObject(result);
        JSONArray arr = obj.getJSONArray("userlist");

        return arr;
    }

    public static JSONObject convertToOpenid(String accessToken, String userId, String agentid){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token=#access_token";
        url = url.replace("#access_token", accessToken);
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("userid",userId);
        param.put("agentid",agentid);
        String detailResult = GetWeixinHttpRequestByte.post(url, JSON.toJSONString(param));
        JSONObject detailObj = JSON.parseObject(detailResult);
        return detailObj;
    }

    /**
     * jsapi生成签名
     * @param jsApiTicket
     * @param nonceStr
     * @param timestamp
     * @param url
     * @return
     */
    public static String generateSignature(String jsApiTicket, String nonceStr, long timestamp, String url) {
        StringBuffer sb = new StringBuffer();
        sb.append("jsapi_ticket=").append(jsApiTicket).append("&");
        sb.append("noncestr=").append(nonceStr).append("&");
        sb.append("timestamp=").append(timestamp).append("&");
        sb.append("url=").append(url);
        return sha1(sb.toString());
    }

    /**
     *  jsapi生成签名
     * @param actName
     * @param mchBillno
     * @param mchId
     * @param noncestr
     * @param reOpenid
     * @param totalAmount
     * @param wxappid
     * @param paySecret
     * @return
     */
    public static String generateQYWXSignature(String actName, String mchBillno, String mchId, String noncestr,String reOpenid,Integer totalAmount,String wxappid,String paySecret) {
        StringBuffer sb = new StringBuffer();
        sb.append("act_name=").append(actName).append("&");
        sb.append("mch_billno=").append(mchBillno).append("&");
        sb.append("mch_id=").append(mchId).append("&");
        sb.append("nonce_str=").append(noncestr).append("&");
        sb.append("re_openid=").append(reOpenid).append("&");
        sb.append("total_amount=").append(totalAmount).append("&");
        sb.append("wxappid=").append(wxappid).append("&");
        sb.append("secret=").append(paySecret);
        return DigestUtils.md5Hex(sb.toString()).toUpperCase();
    }



    /**
     * sha1加密
     * @param str
     * @return
     */
    public static String sha1(String str) {
        if(StringUtil.isBlank(str)) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("utf-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for(int i = 0; i < j; i++) {
                byte b0 = md[i];
                buf[k++] = hexDigits[b0 >>> 4 & 0xf];
                buf[k++] = hexDigits[b0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送企业微信红包
     * @param domain
     * @param mchId
     * @param certPath
     * @param useCert
     * @throws Exception
     */
    public static String sendRedPack(String domain,String mchId,String certPath,String data,boolean useCert) throws Exception{

        BasicHttpClientConnectionManager connManager;
        if (useCert) {
            // 证书
            char[] password = mchId.toCharArray();
            File file = new File(certPath);
            InputStream certStream = new FileInputStream(file);
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(certStream, password);

            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);

            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());

            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslConnectionSocketFactory)
                            .build(),
                    null,
                    null,
                    null
            );
        }
        else {
            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", SSLConnectionSocketFactory.getSocketFactory())
                            .build(),
                    null,
                    null,
                    null
            );
        }

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        String url = "https://" + domain + "/mmpaymkttransfers/sendworkwxredpack";
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 " + mchId);  // TODO: 很重要，用来检测 sdk 的使用情况，要不要加上商户信息？
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }

    public static String queryRedPack(String domain,String mchId,String certPath,String data,boolean useCert) throws Exception{
        BasicHttpClientConnectionManager connManager;
        if (useCert) {
            // 证书
            char[] password = mchId.toCharArray();
            File file = new File(certPath);
            InputStream certStream = new FileInputStream(file);
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(certStream, password);

            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);

            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());

            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslConnectionSocketFactory)
                            .build(),
                    null,
                    null,
                    null
            );
        }
        else {
            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", SSLConnectionSocketFactory.getSocketFactory())
                            .build(),
                    null,
                    null,
                    null
            );
        }

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        String url = "https://" + domain + "/mmpaymkttransfers/queryworkwxredpack";
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeoutMs).setConnectTimeout(connectTimeoutMs).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 " + mchId);  // TODO: 很重要，用来检测 sdk 的使用情况，要不要加上商户信息？
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");
    }

    public static String replaceCDataChar(String text){
        if(StringUtils.isBlank(text)){
            return "";
        }
        return text.replace("<![CDATA[","").replace("]]>","");
    }

    /**
     * @desc ：微信上传素材的请求方法
     *
     * @param accessToken  微信上传临时素材的接口url
     * @param file    要上传的文件
     * @return String  上传成功后，微信服务器返回的消息
     */
    public static JSONObject uploadTempMaterial(String accessToken, String type, MultipartFile file) {

        String path = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=#access_token&type=#type";
        path = path.replace("#access_token", accessToken);
        path = path.replace("#type", type);

        StringBuffer buffer = new StringBuffer();

        try{
            //1.建立连接
            URL url = new URL(path);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  //打开链接

            //1.1输入输出设置
            httpUrlConn.setDoInput(true);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setUseCaches(false); // post方式不能使用缓存
            //1.2设置请求头信息
            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            //1.3设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            httpUrlConn.setRequestProperty("Content-Type","multipart/form-data; boundary="+ BOUNDARY);

            // 请求正文信息
            // 第一部分：
            //2.将文件头输出到微信服务器
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.getSize()
                    + "\";filename=\""+ file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            OutputStream outputStream = new DataOutputStream(httpUrlConn.getOutputStream());
            // 将表头写入输出流中：输出表头
            outputStream.write(head);

            //3.将文件正文部分输出到微信服务器
            // 把文件以流文件的方式 写入到微信服务器中
            InputStream in = file.getInputStream();
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                outputStream.write(bufferOut, 0, bytes);
            }
            in.close();
            //4.将结尾部分输出到微信服务器
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            outputStream.write(foot);
            outputStream.flush();
            outputStream.close();


            //5.将微信服务器返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();


        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        return JSONObject.parseObject(buffer.toString());
    }
}
