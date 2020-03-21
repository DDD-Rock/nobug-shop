package com.nobug.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nobug.ResultBean;
import com.nobug.constant.IConstant;
import com.nobug.dto.ProductTypeDTO;
import com.nobug.service.IndexCacheService;
import com.nobug.service.IndexMapperService;
import com.nobug.service.IIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class IndexServiceImpl implements IIndexService, IConstant {

    @Autowired
    private IndexCacheService indexCacheService;

    @Autowired
    private IndexMapperService indexMapperService;


    @Override
    public ResultBean getGuideList() {
        //先从redis中取
        ResultBean cacheResultBean = indexCacheService.getData(REDIS_INDEX_GUIDE);

        if (cacheResultBean.getErrno() == 1) {
            //redis中没取到
//        ResultBean resultBean = indexMapperService.getProductType();
            //开启分布式锁,防止缓存穿透
            String uuid = UUID.randomUUID().toString();
            ResultBean lockBean = indexCacheService.setnx(REDIS_INDEX_GUIDE_LOCK, uuid);
            if (lockBean.getErrno() == 0) {
                //上锁成功,查询数据库
                ResultBean mapperResultBean = indexMapperService.getProductType();
                if (mapperResultBean.getErrno() == 0) {
                    //从数据库中查询出结果
                    //存缓存中一份(直接存json)
                    List<ProductTypeDTO> data = (List<ProductTypeDTO>) mapperResultBean.getData();
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        String value = objectMapper.writeValueAsString(data);
                        ResultBean setResultBean = indexCacheService.setData(REDIS_INDEX_GUIDE, value);
                        if(setResultBean.getErrno()==0){
                            return ResultBean.success(data);
                        }else{
                            return setResultBean;
                        }
                    } catch (JsonProcessingException e) {
                        log.error("getGuideList()Jackson数据转换异常" + e);
                        //TODO 部署时可以删掉
                        e.printStackTrace();
                        return ResultBean.error("错误101,获取商品类型列表失败,请联系管理员.");
                    }

                } else {
                    //数据库中也没有结果,返回错误
                    log.error("首页获取商品类型列表失败");
                    return ResultBean.error("当前没有商品类型列表,请联系管理员.");
                }

            } else {
                //上锁失败,线程休眠一秒,再重新调用这个方法获取数据
                try {
                    Thread.sleep(1000);
                    return getGuideList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }

        //取到了数据(json字符串)
        String data = (String) cacheResultBean.getData();
        //用jackson转换
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<ProductTypeDTO> result = objectMapper.convertValue(data, new TypeReference<List<ProductTypeDTO>>() {
//        });

        return ResultBean.success(data);

    }
}
