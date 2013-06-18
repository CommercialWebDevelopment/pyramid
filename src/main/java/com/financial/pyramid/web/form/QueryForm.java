package com.financial.pyramid.web.form;

/**
 * User: Danil
 * Date: 18.06.13
 * Time: 22:51
 */
public class QueryForm {
    public int page = 1;
    public int count = 10;      // количество на странице
    public String sort = "name";
    public String order = "asc";
    public String query;        // строка запроса
    public String field;        // поле запроса

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
