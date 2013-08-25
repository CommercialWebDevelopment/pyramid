package com.financial.pyramid.web.form;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Danil
 * Date: 17.06.13
 * Time: 22:28
 */
public class PageForm<T> {
    public int page;
    public int total;
    public List<T> rows = new ArrayList<T>();

    public PageForm() {
    }

    public PageForm(List<T> rows) {
        this.page = 1;
        this.total = rows.size();
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
