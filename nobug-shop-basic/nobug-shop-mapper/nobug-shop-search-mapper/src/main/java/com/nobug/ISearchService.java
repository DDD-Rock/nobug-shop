package com.nobug;


public interface ISearchService {

    ResultBean searchByKeyword(String keyword);

    ResultBean addroduct(Long pid);
}
