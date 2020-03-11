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
import java.util.Map;

@Service
public class SearchServiceImpl implements ISearchService {


    @Autowired
    private SolrClient solrClient;


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

        String url = "http://localhost/";

        ResultBean bean = new ResultBean();

        SolrQuery solrQuery = new SolrQuery();

        //赋值域
        solrQuery.setQuery(keyWord);
        solrQuery.set("df", "t_item_keywords");

        //分页
        solrQuery.setStart(0);
        solrQuery.setRows(2);
        //开启高亮现实
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("t_product_name");
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost("</span>");

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

        //获取高亮
        Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();

        for (SolrDocument result : results) {

            TProductSearchDTO product = new TProductSearchDTO();

            String id_str = result.getFieldValue("id").toString();


            product.setId(Long.parseLong(id_str));
            //高亮封装
            Map<String, List<String>> id = highlighting.get(id_str);
            List<String> t_product_names = id.get("t_product_name");
            //获取高亮字段值
            String t_product_name = t_product_names.get(0);
            product.settProductName(t_product_name);
            product.settProductSalePrice(Double.parseDouble(result.getFieldValue("t_product_sale_price").toString()));
            //判断图片和样式是否为空
            String t_product_pimage = result.getFieldValue("t_product_pimage").toString();
            if(t_product_pimage == null){
                t_product_pimage = "";
            }else{
                t_product_pimage += url;
            }
            product.settProductPimage(t_product_pimage);

            String t_product_pdesc = (String)result.getFieldValue("t_product_pdesc");
            if(t_product_pdesc == null){
                t_product_pdesc ="";
            }
            product.settProductPdesc(t_product_pdesc);


            list.add(product);


        }
        bean.setData(list);

        return bean;
    }

    }
