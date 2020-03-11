package com.nobug;


public interface ISearchService {

    ResultBean insertSolr();

    ResultBean searchByKeyword(String keyword);
}
