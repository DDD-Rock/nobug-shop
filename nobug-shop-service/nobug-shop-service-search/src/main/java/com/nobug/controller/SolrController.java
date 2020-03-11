package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SolrController {

    @Autowired
    private SearchServiceImpl service;

    @RequestMapping("solr")
    @ResponseBody
    public ResultBean solr(){

        ResultBean bean = service.insertSolr();

        return bean;

    }

    @RequestMapping("/Keyword")
    @ResponseBody
    public ResultBean searchByKeyword(@RequestParam String keyWord){

        ResultBean bean = service.searchByKeyword(keyWord);

        return bean;
    }
}
