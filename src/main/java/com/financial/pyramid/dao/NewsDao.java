package com.financial.pyramid.dao;

import com.financial.pyramid.domain.News;
import com.financial.pyramid.web.form.QueryForm;

import java.util.List;

/**
 * User: dbudunov
 * Date: 21.06.13
 * Time: 19:51
 */
public interface NewsDao extends AbstractDao<News, Long> {
    public List<News> findByQuery(QueryForm form);
}
