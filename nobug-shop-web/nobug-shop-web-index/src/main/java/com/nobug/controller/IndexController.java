package com.nobug.controller;


import com.nobug.ResultBean;
import com.nobug.service.IIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("index")
public class IndexController {

@Autowired
private IIndexService indexService;

    @RequestMapping("guide")
    public ResultBean getGuideList(){
        ResultBean resultBean = indexService.getGuideList();
        return resultBean;
    }

}
