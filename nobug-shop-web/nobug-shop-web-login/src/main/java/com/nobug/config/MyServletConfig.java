package com.nobug.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyServletConfig {

    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean bean = new ServletRegistrationBean(streamServlet);
        bean.setLoadOnStartup(1);
        bean.addUrlMappings("/hystrix.stream");
        bean.setName("HystrixMetricsStreamServlet");

        return bean;
    }
}
