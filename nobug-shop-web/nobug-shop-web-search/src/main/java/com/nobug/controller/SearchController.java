package com.nobug.controller;

import com.nobug.ResultBean;
import com.nobug.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * 使用复制域搜索查询
     * @param keyWord    查询条件
     * @return
     */
    @RequestMapping("/search/keyword")
    public String searchByKeyword(@RequestParam String keyWord, Model model){

        ResultBean bean = searchService.searchByKeyword(keyWord);

        model.addAttribute("products",bean.getData());

        return "search";

    }



}
