package com.nobug.service;

import com.nobug.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class SearchService {


    @Autowired
    private RestTemplate restTemplate;


    public ResultBean insertSolr(){


        String url = "http://nobug-shop-service-search/solr";

            ResultBean bean = restTemplate.getForObject(url, ResultBean.class);

        return bean;


    }

    public ResultBean searchByKeyword(String keyWord){

        String url = "http://nobug-shop-service-search/Keyword?keyWord="+keyWord;

        ResultBean bean = restTemplate.getForObject(url, ResultBean.class);

        return bean;
    }


}
