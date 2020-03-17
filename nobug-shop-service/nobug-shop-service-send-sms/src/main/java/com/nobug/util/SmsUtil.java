package com.nobug.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
 * 美圣融云
 * http://www.rcscloud.cn/sms
 */
public class SmsUtil {

    /**
     *	账号
     */
    static String ACCOUNT_SID = "ZH000001303";

    /**
     * APIKEY
     */
    static String ACCOUNT_APIKEY = "f1d694fa-6ab7-44b8-af00-bdeed24734fd";

    /**
     * utf8编码
     */
    static final String CHARSET_UTF8 = "utf-8";

    /**
     * HttpUrl
     */
    static String HttpUrl = "http://api.rcscloud.cn:8030/rcsapi/rest";  //或使用ip:  http://121.41.114.153:8030/rcsapi/rest

    //以下为接口调用的封装 ，可以直接使用
    /**
     * 发送模板短信
     * @param tplId 模板id
     * @param mobile 手机号码
     * @param content 参数值，多个参数以“||”隔开   如:@1@=HY001||@2@=3281
     * @param extno 自定义扩展码，建议1-4位，需申请开通自定义扩展
     * @return json字符串,详细描述请参考接口文档
     *
     * String
     */
    public static String sendTplSms(String tplId,String mobile,String content,String extno){
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String resultJson = "";
        try {
            //签名:Md5(sid+key+tplid+mobile+content)
            StringBuilder signStr = new StringBuilder();
            signStr.append(ACCOUNT_SID).append(ACCOUNT_APIKEY).append(tplId).append(mobile).append(content);
            /**
             * 注意：发送短信返回1005错误码时，主要由于中文字符转码错误造成签名鉴权失败
             * 在实际开发中采用采用UTF-8或者GB2312转码
             * **/
            //String sign = md5Digest(changeCharset(signStr.toString(), "GB2312"));
            String sign = md5Digest(changeCharset(signStr.toString(), "UTF-8"));

            //创建HttpPost请求
            HttpPost httppost = new HttpPost(HttpUrl +"/sms/sendtplsms.json");//?sid="+ACCOUNT_SID+"&sign="+sign
            //构建form
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("sid", ACCOUNT_SID));
            nvps.add(new BasicNameValuePair("sign", sign));
            nvps.add(new BasicNameValuePair("tplid", tplId));
            nvps.add(new BasicNameValuePair("mobile", mobile));
            nvps.add(new BasicNameValuePair("content", content));
            nvps.add(new BasicNameValuePair("extno", extno));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps,CHARSET_UTF8);
            httppost.setEntity(entity);

            //设置请求表头信息，POST请求必须采用application/x-www-form-urlencoded否则提示415错误
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.setHeader("Content-Encoding", CHARSET_UTF8);
            //执行请求
            HttpResponse response = httpclient.execute(httppost);
            //获取响应Entity
            HttpEntity httpEntity = response.getEntity();
            //返回JSON字符串格式，用户根据实际业务进行解析处理
            if (httpEntity != null) {
                resultJson = EntityUtils.toString(httpEntity, CHARSET_UTF8);
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        System.out.println("resultJson=" +resultJson);
        return resultJson;
    }

    /**
     * 查询账号信息
     * 	/user/get.json?sid={sid}&sign={sign}
     * @return json字符串,详细描述请参考接口文档
     * String
     */
    public static String queryUser(){
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String resultJson = "";
        try {
            //签名
            String sign = md5Digest(ACCOUNT_SID + ACCOUNT_APIKEY);
            //请求url
            StringBuilder url = new StringBuilder();
            url.append(HttpUrl).append("/user/get.json").append("?sid=").append(ACCOUNT_SID).append("&sign=").append(sign);
            //GET请求
            HttpGet httpget =  new HttpGet(url.toString());
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resultJson = EntityUtils.toString(entity, CHARSET_UTF8);
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        System.out.println("resultJson=" + resultJson);
        return resultJson;
    }

    /**
     * 查询账号下所有模板信息
     * @return json字符串,详细描述请参考接口文档
     * String
     */
    public static String queryTpls(){
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String resultJson = "";
        try {
            //签名
            String sign = md5Digest(ACCOUNT_SID + ACCOUNT_APIKEY);
            //请求url
            StringBuilder url = new StringBuilder();
            url.append(HttpUrl).append("/tpl/gets.json").append("?sid=").append(ACCOUNT_SID).append("&sign=").append(sign);
            //Http GET方式
            HttpGet httpget =  new HttpGet(url.toString());
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resultJson = EntityUtils.toString(entity, CHARSET_UTF8);
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        System.out.println("resultJson=" + resultJson);
        return resultJson;
    }
    /**
     * 查询指定模板
     * @param tplId 模板id
     * @return json字符串,详细描述请参考接口文档
     * String
     */
    public static String queryTplById(String tplId){
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String resultJson = "";
        try {
            //签名
            String sign = md5Digest(ACCOUNT_SID + ACCOUNT_APIKEY + tplId);
            //
            StringBuilder url = new StringBuilder();
            url.append(HttpUrl).append("/tpl/get.json").append("?sid=").append(ACCOUNT_SID).append("&sign=").append(sign).append("&tplid=").append(tplId);
            //GET请求
            HttpGet httpget =  new HttpGet(url.toString());
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resultJson = EntityUtils.toString(entity, CHARSET_UTF8);
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        System.out.println("resultJson=" + resultJson);
        return resultJson;
    }

    /**
     * 查询账号下所有模板信息
     * @return
     * String
     */
    public static String queryRpt(){
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String resultJson = "";
        try {
            //签名
            String sign = md5Digest(ACCOUNT_SID + ACCOUNT_APIKEY);
            //请求url
            StringBuilder url = new StringBuilder();
            url.append(HttpUrl).append("/sms/queryrpt.json").append("?sid=").append(ACCOUNT_SID).append("&sign=").append(sign);
            //GET请求
            HttpGet httpget =  new HttpGet(url.toString());
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resultJson = EntityUtils.toString(entity, CHARSET_UTF8);
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        System.out.println("resultJson=" + resultJson);
        return resultJson;
    }


    /**
     * 获取上行短信，采用GET方式
     * /sms/querymo.json?sid={sid}&sign={sign}
     * @return json字符串,详细描述请参考接口文档
     * String
     */
    public static String queryMo(){
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String resultJson = "";
        try {
            //签名
            String sign = md5Digest(ACCOUNT_SID + ACCOUNT_APIKEY);
            //请求url
            StringBuilder url = new StringBuilder();
            url.append(HttpUrl).append("/sms/querymo.json").append("?sid=").append(ACCOUNT_SID).append("&sign=").append(sign);
            //GET请求
            HttpGet httpget =  new HttpGet(url.toString());
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resultJson = EntityUtils.toString(entity, CHARSET_UTF8);
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        System.out.println("resultJson=" + resultJson);
        return resultJson;
    }


    /**
     * 校验黑名单，采用GET方式
     * /assist/bl.json?sid={sid}&sign={sign}&mobile={mobile}
     * @return json字符串,详细描述请参考接口文档
     * String
     */
    public static String validBL(String mobile){
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String resultJson = "";
        try {
            //签名，MD5 32位
            String sign = md5Digest(ACCOUNT_SID + ACCOUNT_APIKEY);
            StringBuilder url = new StringBuilder();
            url.append(HttpUrl).append("/assist/bl.json")
                    .append("?sid=").append(ACCOUNT_SID)
                    .append("&sign=").append(sign)
                    .append("&mobile=").append(mobile);
            //GET请求
            HttpGet httpget =  new HttpGet(url.toString());
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resultJson = EntityUtils.toString(entity, CHARSET_UTF8);
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (httpclient != null)
                httpclient.getConnectionManager().shutdown();
        }
        System.out.println("resultJson=" + resultJson);
        return resultJson;
    }

    /**
     * 校验敏感词，采用GET方式
     * /assist/sw.json?sid={sid}& sign={sign}&content={content}
     * @param content 内容
     * @return json字符串,详细描述请参考接口文档
     */
    public static String validSW(String content){
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String resultJson = "";
        try {
            //签名
            String sign = md5Digest(ACCOUNT_SID + ACCOUNT_APIKEY);
            StringBuilder url = new StringBuilder();
            url.append(HttpUrl).append("/assist/sw.json")
                    .append("?sid=").append(ACCOUNT_SID)
                    .append("&sign=").append(sign)
                    .append("&content=").append(content);
            //GET请求
            HttpGet httpget =  new HttpGet(url.toString());
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resultJson = EntityUtils.toString(entity, CHARSET_UTF8);
            }
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            if (httpclient != null)
                httpclient.getConnectionManager().shutdown();
        }
        System.out.println("resultJson=" + resultJson);
        return resultJson;
    }


    /**
     * MD5算法
     * @param src
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * String
     */
    public static String md5Digest(String src) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(src.getBytes(CHARSET_UTF8));
        return byte2HexStr(b);
    }

    private static String byte2HexStr(byte[] b){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; ++i) {
            String s = Integer.toHexString(b[i] & 0xFF);
            if (s.length() == 1) {
                sb.append("0");
            }
            sb.append(s.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 字符编码转换
     * @param str
     * @param newCharset
     * @return
     * @throws UnsupportedEncodingException
     * String
     */
    public static String changeCharset(String str, String newCharset)
            throws UnsupportedEncodingException {
        if (str != null) {
            //用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            //用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

}
