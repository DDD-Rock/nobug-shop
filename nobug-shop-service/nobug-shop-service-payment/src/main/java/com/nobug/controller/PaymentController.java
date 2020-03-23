package com.nobug.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.google.gson.Gson;
import com.nobug.entity.Message;
import com.nobug.entity.TOrder;
import com.nobug.entity.TOrderpay;
import com.nobug.entity.TOrdership;
import com.nobug.service.MessageService;
import com.nobug.service.TOrderpayService;
import com.nobug.service.TOrdershipService;
import com.nobug.service.impl.AnnotationQuartz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.annotation.Order;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;



/**
 * (Payment)表控制层
 */
@Controller
@Slf4j
public class PaymentController {
    /**
     * 服务对象
     */


    /**
     * 支付服务，服务成功返回success页面
     *
     *
     */

    @Resource
    @LoadBalanced
    private RestTemplate restTemplate;

    private final static String Message_URI ="http://NOBUG-SHOP-SERVICE-MQ/mq/";

    @Resource
    private TOrdershipService tOrdershipService;


    //初始化
    Message message = new Message();





    @RequestMapping("success")
    public String getSuccess(HttpServletRequest request, Model model, Integer orderId) {
        String total_amount = request.getParameter("total_amount");
        String out_trade_no = request.getParameter("out_trade_no");

        TOrdership tOrdership = tOrdershipService.queryByOrderId(orderId);
        model.addAttribute("total_amount",total_amount);
        model.addAttribute("orderId",out_trade_no);
        model.addAttribute("tOrdership",tOrdership);

        return "success";
    }

    @RequestMapping("pay")
    public String showPay(){
        return "pay";
    }

    @RequestMapping("doPay")
    public void doPay(HttpServletRequest httpRequest,
                       HttpServletResponse httpResponse,String oid) throws ServletException, IOException {
        TOrder order = (TOrder) httpRequest.getAttribute("order");

        //初始化消息
        message.setId(order.getId());
        message.setStatus(0);
        restTemplate.getForObject(Message_URI +"rec"+message, Message.class,message);

        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",
                "2016101900721641",
                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC5XIR0Vg4LXI0LlvwZqavnD9l+DZJA3Vcy9lHatcxSjpkMaqpzusgZXJwUF4F0BWxvI7QhWk1exjZ48Llf5b5vbRVX1wUeSiO3K6+iVli5mlrbeE3wj6kdke7aOFKWeBSGh/G5UiDNhXfjQ/s5xYyDteBXBFozAo1eOhlNCl6vFPP4HP4Um2MapQFxQ9NPVov9iurw2UosIz6soBcNxBhgf3cLfPmW+4Eo/d/MS1Eyz8xX5MUFLGVWbFdb+jUZYDeTr+h5iA1wm9wk2dzFXM/YRyY02oXt0W1i1H5EL4SOsxe1kVa422HdAmylVuFdY55xzDvPdbI1yNxAoxBRN5EJAgMBAAECggEBAJJt38T3ZkjbaCDLuYOcYcw63RmTJwJO2F/N7oPUMgDV3VnNubGK0CAY5MOKfh1lW2/AyL/AUnaYJtgLcqWrHHFxvvaRSd3Pu78rp6eahqS4pyXRN+Dd3D8b1ZBWBggP/Eb6hZ5cnsU8tqS2Q3Qz9vjlaL/VFFxPm9XgF7SgiIPNtIaQj2xhl6OOcwM2sfkfKvzqhzIoXPyfMMXzGoIuT2yZP9yWMScAYWgHTpC9MsWPgKHq34+uuCAV0HROhqZbbNXUpVIPw/v1F1IOXIR4Rj+cbE2OOGASN+2iaNbmHa4fxhGELUWSqx+ikqofDOyk98IMK+4DCoVOtpAAcLGijgECgYEA6lTsowqsxFnFIBsOwmkeC0Lp8DIEeU7LzeJ7p1HxFc7bgT6ePQ1TmMfI73le+ZexLtyGD/mTsXcXxYkUqR6OnW50vFriSDX/+cIvwf9dnFPLjUC3sfRpYe8jbcLtdVHR9T6U9+AhobVR+Sw0Supel+79DCoUr1uxm8PHOPZ0ELECgYEAyoBfVzyPuGGgz90XgqEvrkyrcSi6/Rr97v51XrelluWysXlzpOpoRs2DY6zM4E4RqzioYHUgSfEaI8NGufEd5NMpHkfhprYVh2f0+atKkNDWIGQDd/2MFQaUNuO5JW2/CN1FmTSyyPIAJjFtec+ObanafW0XyVpAuqtOCrru29kCgYEA534kE+GM0aC5a8EvMIG163wcLWzMHKbqEaenbqE1oMys7p5Upo2Ow0TCzUjCuaHQqTGzwv5UmKHFOyDz5yrHyuD+s8C5AItQKIVctrK24KOrWAzSLBv/K3+aKWnDOf4tg7BibAngT6cXpyezNTsZXdD77VN5Ac98wxuCqVRXTtECgYEAg81KQN2KLdhdcu3Uf5GqFyiP7fc0vcjzvrqgaiXeAXk/9YO3YX+wn2TkP9wY/WXS2j7mWOHjQj1LZjuTrTLi4i0OdkcS+A9Ls/ZV3KtAvUEwHaT7HP5KTuUZUyClQVdNH9fllPJX67KXpkRFnSMa8QW80CZRdRzpRJZ7FUDWdwECgYAa1dGElBkGDuoBT78kr9VsxQTjQjY6tqb011JuyD+2YBAQwuR4XLfNzWmFOzaafwXt6hdHvEQq5dFebjiNS9FuEv2PsnuxLPiqxeDTZ/ZaCa41AGILpkip9JUUnld7DtIU0jayhvX1NtCcE6w9gWRSx5KMffxncduwifYhFW/uIA==",
                "JSON",
                "utf-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt2K44J7w9S2skUCrj7Q1NOuXYXFKZIphkhfx0P+7ZaTediwx3hJ3n6Q0dUg6zi9DtfA2QeGdOjSbVRaY6bYbfIjwrEuQL//vPeOfY6m8meOKXQtLYYdp0ndS7OBNAiNdUMzC2ZtWuBMCZEFaekEdbYGjT+4uEmYc882gxAT8JMpWK4jSxdnU4Hgi7QAQ3EGlOKKrqGVWaMJemuPhm6/Vj7CnEv0HhUO22jSY21dLgrIs2+nRJwbjucYm84JLmzT7HOeybzAEeeTfxshnd5LCro1B9Bw/kKe4UgstJE0Qk/vCsHjMCWt1ZJexvVGku15faYoiviw00L04DcKstV0opQIDAQAB",
                "RSA2"); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(" http://j6xt27.natappfree.cc/success");
        alipayRequest.setNotifyUrl(" http://j6xt27.natappfree.cc/notifyUrl");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
               "    \"out_trade_no\":\"" + order.getId() + "\"," +
//                "    \"out_trade_no\":\"" + "20200313105750007"+ "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
               "    \"total_amount\":"+order.getAmount()+","+
 //               "    \"total_amount\":"+"6"+","+
             "    \"subject\":\""+order.getRemark()+"\"," +
 //               "    \"subject\":\""+"教育培训"+"\"," +
                "    \"body\":\""+order.getRemark()+"\"," +
//                "    \"body\":\""+"qf"+"\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=utf-8" );
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }
    //验签
    @RequestMapping("notifyUrl")
    @ResponseBody
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {



        //获取到请求中所有的键值对
        Map<String, String[]> parameterMap = request.getParameterMap();
        //需要将map中的String[]==>String
        Map<String, String> paramsMap = new HashMap<>(); //将异步通知中收到的所有参数都存放到 map 中
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            String[] values = entry.getValue();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < values.length - 1; i++) {
                sb.append(values[i] + ",");
            }
            //单独添加最后一个值，防止多个“，”号
            sb.append(values[values.length - 1]);
            //将key和value放入paramsMap中
            paramsMap.put(entry.getKey(), sb.toString());
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap,
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuVyEdFYOC1yNC5b8Gamr5w/Zfg2SQN1XMvZR2rXMUo6ZDGqqc7rIGVycFBeBdAVsbyO0IVpNXsY2ePC5X+W+b20VV9cFHkojtyuvolZYuZpa23hN8I+pHZHu2jhSlngUhofxuVIgzYV340P7OcWMg7XgVwRaMwKNXjoZTQperxTz+Bz+FJtjGqUBcUPTT1aL/Yrq8NlKLCM+rKAXDcQYYH93C3z5lvuBKP3fzEtRMs/MV+TFBSxlVmxXW/o1GWA3k6/oeYgNcJvcJNncxVzP2EcmNNqF7dFtYtR+RC+EjrMXtZFWuNth3QJspVbhXWOeccw7z3WyNcjcQKMQUTeRCQIDAQAB",
                "utf-8",
                "RSA2"); //调用SDK验证签名
        if (signVerified) {
            // 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            Gson gson = new Gson();
            HashMap<String, Object> jsonMap = new HashMap<>();
            HashMap<String, Object> map = new HashMap<>();
            map.put("code","222");
            map.put("msg","Success");
            map.put("trade_no", paramsMap.get("trade_no"));
            map.put("out_trade_no",paramsMap.get("out_trade_no"));
            map.put("seller_id",paramsMap.get("seller_id"));
            map.put("total_amount",paramsMap.get("total_amount"));
            map.put("merchant_order_no",paramsMap.get("merchant_order_no"));
            jsonMap.put("alipay_trade_page_pay_response",map);
            jsonMap.put("sign","MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAljb6rHchsCb");
            TOrder order = (TOrder) request.getAttribute("order");



            //验签成功
            if (paramsMap.get("out_trade_no").equals(order.getId()) &&
                    paramsMap.get("total_amount").equals(order.getAmount())) {
                    //paramsMap.get("total_amount").equals("6")) {
                log.info("金额正确，验签成功");
                response.getWriter().write(gson.toJson(jsonMap));
                //验签成功向发送消息
                //使用mq的方式解决分布式事务,向mq服务发送send消息

                message.setId(order.getId());
                message.setStatus(1);
                restTemplate.getForObject(Message_URI +"rec"+message, Message.class,message);

            }
            //验签失败
            map.put("msg","failure");
            jsonMap.put("alipay_trade_page_pay_response",map);
            response.getWriter().write(gson.toJson(jsonMap));
        } else {
            // 验签失败则记录异常日志，并在response中返回failure.
            log.info("【验签失败】-->支付结果异常");
            //响应json
            Map<String, Object> jsonMap = new HashMap<>();
            Map<String, Object> map = new HashMap<>();
            Gson gson = new Gson();
            map.put("code","555");
            map.put("msg","Service Currently Unavailable");
            map.put("sub_code","isp.unknow-error");
            map.put("sub_msg","系统繁忙");
            jsonMap.put("alipay_trade_page_pay_response",map);
            jsonMap.put("sign","MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAljb6rHchsCb");
            response.getWriter().write(gson.toJson(jsonMap));
        }
    }
}