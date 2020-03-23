package com.nobug.impl;


import com.nobug.ISearchService;
import com.nobug.ResultBean;
import entity.TProductSearchDTO;
import com.nobug.mapper.TProductSearchDTOMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
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

        // 创建一个集合，用于存放将要插入到solr中的数据
        ArrayList<SolrInputDocument> docs = new ArrayList<>();
        for (TProductSearchDTO product: list) {
            SolrInputDocument fields = new SolrInputDocument();
            fields.setField("id",product.getId());
            fields.setField("t_product_name",product.gettProductName());
            fields.setField("t_product_sale_price",product.gettProductSalePrice());
            fields.setField("t_product_pimage",product.gettProductPimage());
            fields.setField("t_product_pdesc",product.gettProductPdesc());
            //将数据添加到集中中
            docs.add(fields);
        }
        try {
            //最后将所有数据添加到solr库中
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

        // 创建solrQuery
        SolrQuery solrQuery = new SolrQuery();

        //赋值域
        solrQuery.setQuery(keyWord);
        solrQuery.set("df", "t_item_keywords");

        //分页
        solrQuery.setStart(0);
        solrQuery.setRows(10);
        //开启高亮现实
        solrQuery.setHighlight(true);
        //需要开启高亮的字段
        solrQuery.addHighlightField("t_product_name");
        //设置高亮的样式
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

        //用于接收解析结果
        ArrayList<TProductSearchDTO> list = new ArrayList<>();
        //拿到查询到的结果
        SolrDocumentList results = query.getResults();

        //获取高亮
        Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();

        for (SolrDocument result : results) {

            TProductSearchDTO product = new TProductSearchDTO();

            //获取solr中查询的数据

            String id_str = result.getFieldValue("id").toString();

            product.setId(Long.parseLong(id_str));
            //高亮封装
            //根据id获取到集合中的每一条数据的map集合
            Map<String, List<String>> id = highlighting.get(id_str);
            //获取数据中的高亮显示字段的集合
            List<String> t_product_names = id.get("t_product_name");
            //获取高亮字段值
            String t_product_name = t_product_names.get(0);
            //封装到product对象中
            product.settProductName(t_product_name);

            product.settProductSalePrice(Double.parseDouble(result.getFieldValue("t_product_sale_price").toString()));
            //判断图片和样式是否为空
            String t_product_pimage = result.getFieldValue("t_product_pimage").toString();
            if(t_product_pimage == null){
                t_product_pimage = "";
            }
            product.settProductPimage(t_product_pimage);

            String t_product_pdesc = (String)result.getFieldValue("t_product_pdesc");
            if(t_product_pdesc == null){
                t_product_pdesc ="";
            }
            product.settProductPdesc(t_product_pdesc);


            list.add(product);


        }
        //将数据封装到一个bean中
        bean.setData(list);

        return bean;
    }

    }
