package com.nobug.impl;


import com.nobug.ISearchService;
import com.nobug.ResultBean;
import com.nobug.entity.TProductSearchDTO;
import com.nobug.mapper.TProductSearchDTOMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements ISearchService {


    @Autowired
    private SolrClient solrClient;

    @Value("@{imageService}")
    private String imageService;

    @Autowired
    private TProductSearchDTOMapper tProductSearchDTOMapper;


    @Override
    public ResultBean insertSolr() {

        ResultBean bean = new ResultBean();

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
            bean.setMessage("插入solr成功");

        } catch (Exception e) {
            bean.setMessage("插入solr失败");
            e.printStackTrace();
        }


        return bean;
    }

    @Override
    public ResultBean searchByKeyword(String keyWord) {

        ResultBean bean = new ResultBean();

        //设置查询的参数，并封装到solrQuery对象
        SolrQuery solrQuery = new SolrQuery();

        //设置复制域
        solrQuery.setQuery(keyWord);
        solrQuery.set("df","t_item_keywords");

        //分页
        solrQuery.setStart(0);
        solrQuery.setRows(10);

        //将参数提交给solr，查询结果
        QueryResponse query = null;
        try {
            query = solrClient.query(solrQuery);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //解析结果
        ArrayList<TProductSearchDTO> list = new ArrayList<>();
        SolrDocumentList results = query.getResults();
        for (SolrDocument r: results) {

            TProductSearchDTO product = new TProductSearchDTO();


            product.setId(Long.parseLong(r.getFieldValue("id").toString()));
            product.settProductName(r.getFieldValue("t_product_name").toString());
            product.settProductPimage(r.getFieldValue("t_product_pimage").toString());
            String s = r.getFieldValue("t_product_sale_price").toString();
            double v = Double.parseDouble(s);
            product.settProductSalePrice(v);
            product.settProductPdesc((String) r.getFieldValue("t_product_pdesc"));

            list.add(product);

        }
        bean.setData(list);

        return bean;
    }
}
