package com.nobug.impl;


import com.nobug.ISearchService;
import com.nobug.ResultBean;
import com.nobug.entity.TProductSearchDTO;
import com.nobug.mapper.TProductSearchDTOMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchServiceImpl implements ISearchService {


    @Autowired
    private SolrClient solrClient;

    @Value("@{imageService}")
    private String imageService;

    @Autowired
    private TProductSearchDTOMapper tProductSearchDTOMapper;


    @Override
    public ResultBean insertSolr() {

        //获取查询的数据
        List<TProductSearchDTO> list = tProductSearchDTOMapper.selectAll();
        System.out.println(list);

        ArrayList<SolrInputDocument> docs = new ArrayList<>();
        for (TProductSearchDTO product: list) {
            SolrInputDocument fields = new SolrInputDocument();
            fields.setField("id",product.getId());
            fields.setField("t_product_name",product.gettProductName());
            fields.setField("t_product_sale_price",product.gettProductSalePrice());
            fields.setField("t_product_pimage",product.gettProductPimage());
            fields.setField("t_product_pdesc",product.gettProductPdesc());
            docs.add(fields);
        }
        try {
            solrClient.add(docs);
            solrClient.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                solrClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
