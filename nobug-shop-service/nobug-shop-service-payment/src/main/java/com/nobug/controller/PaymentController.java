package com.nobug.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.nobug.dao.CommonResult;
import com.nobug.entity.TPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.catalina.manager.Constants.CHARSET;

/**
 * (Payment)表控制层
 *
 * @author makejava
 * @since 2020-03-10 21:26:07
 */
@RestController
@Slf4j
public class PaymentController {
    /**
     * 服务对象
     */


    /**
     * 通过主键查询单条数据
     *
     * @param
     * @return 单条数据
     */

    @RequestMapping("success")
    public String getSuccess(){
        return "success";
    }

    @RequestMapping("pay")
    public String showPay(){
        return "pay";
    }

    @RequestMapping("doPay")
    public void doPay(HttpServletRequest httpRequest,
                       HttpServletResponse httpResponse) throws ServletException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do",
                "2016101900721641",
                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC5XIR0Vg4LXI0LlvwZqavnD9l+DZJA3Vcy9lHatcxSjpkMaqpzusgZXJwUF4F0BWxvI7QhWk1exjZ48Llf5b5vbRVX1wUeSiO3K6+iVli5mlrbeE3wj6kdke7aOFKWeBSGh/G5UiDNhXfjQ/s5xYyDteBXBFozAo1eOhlNCl6vFPP4HP4Um2MapQFxQ9NPVov9iurw2UosIz6soBcNxBhgf3cLfPmW+4Eo/d/MS1Eyz8xX5MUFLGVWbFdb+jUZYDeTr+h5iA1wm9wk2dzFXM/YRyY02oXt0W1i1H5EL4SOsxe1kVa422HdAmylVuFdY55xzDvPdbI1yNxAoxBRN5EJAgMBAAECggEBAJJt38T3ZkjbaCDLuYOcYcw63RmTJwJO2F/N7oPUMgDV3VnNubGK0CAY5MOKfh1lW2/AyL/AUnaYJtgLcqWrHHFxvvaRSd3Pu78rp6eahqS4pyXRN+Dd3D8b1ZBWBggP/Eb6hZ5cnsU8tqS2Q3Qz9vjlaL/VFFxPm9XgF7SgiIPNtIaQj2xhl6OOcwM2sfkfKvzqhzIoXPyfMMXzGoIuT2yZP9yWMScAYWgHTpC9MsWPgKHq34+uuCAV0HROhqZbbNXUpVIPw/v1F1IOXIR4Rj+cbE2OOGASN+2iaNbmHa4fxhGELUWSqx+ikqofDOyk98IMK+4DCoVOtpAAcLGijgECgYEA6lTsowqsxFnFIBsOwmkeC0Lp8DIEeU7LzeJ7p1HxFc7bgT6ePQ1TmMfI73le+ZexLtyGD/mTsXcXxYkUqR6OnW50vFriSDX/+cIvwf9dnFPLjUC3sfRpYe8jbcLtdVHR9T6U9+AhobVR+Sw0Supel+79DCoUr1uxm8PHOPZ0ELECgYEAyoBfVzyPuGGgz90XgqEvrkyrcSi6/Rr97v51XrelluWysXlzpOpoRs2DY6zM4E4RqzioYHUgSfEaI8NGufEd5NMpHkfhprYVh2f0+atKkNDWIGQDd/2MFQaUNuO5JW2/CN1FmTSyyPIAJjFtec+ObanafW0XyVpAuqtOCrru29kCgYEA534kE+GM0aC5a8EvMIG163wcLWzMHKbqEaenbqE1oMys7p5Upo2Ow0TCzUjCuaHQqTGzwv5UmKHFOyDz5yrHyuD+s8C5AItQKIVctrK24KOrWAzSLBv/K3+aKWnDOf4tg7BibAngT6cXpyezNTsZXdD77VN5Ac98wxuCqVRXTtECgYEAg81KQN2KLdhdcu3Uf5GqFyiP7fc0vcjzvrqgaiXeAXk/9YO3YX+wn2TkP9wY/WXS2j7mWOHjQj1LZjuTrTLi4i0OdkcS+A9Ls/ZV3KtAvUEwHaT7HP5KTuUZUyClQVdNH9fllPJX67KXpkRFnSMa8QW80CZRdRzpRJZ7FUDWdwECgYAa1dGElBkGDuoBT78kr9VsxQTjQjY6tqb011JuyD+2YBAQwuR4XLfNzWmFOzaafwXt6hdHvEQq5dFebjiNS9FuEv2PsnuxLPiqxeDTZ/ZaCa41AGILpkip9JUUnld7DtIU0jayhvX1NtCcE6w9gWRSx5KMffxncduwifYhFW/uIA==",
                "JSON",
                "utf-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt2K44J7w9S2skUCrj7Q1NOuXYXFKZIphkhfx0P+7ZaTediwx3hJ3n6Q0dUg6zi9DtfA2QeGdOjSbVRaY6bYbfIjwrEuQL//vPeOfY6m8meOKXQtLYYdp0ndS7OBNAiNdUMzC2ZtWuBMCZEFaekEdbYGjT+4uEmYc882gxAT8JMpWK4jSxdnU4Hgi7QAQ3EGlOKKrqGVWaMJemuPhm6/Vj7CnEv0HhUO22jSY21dLgrIs2+nRJwbjucYm84JLmzT7HOeybzAEeeTfxshnd5LCro1B9Bw/kKe4UgstJE0Qk/vCsHjMCWt1ZJexvVGku15faYoiviw00L04DcKstV0opQIDAQAB",
                "RSA2"); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"20150320010101001\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":88.88," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"body\":\"Iphone6 16G\"," +
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


}