package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {


    @Autowired
    private SearchService searchService;



    @RequestMapping("/search/solr")
    @ResponseBody
    public String insertSolr(){

        ResultBean bean = searchService.insertSolr();

        return bean.getMessage();
    }



}
