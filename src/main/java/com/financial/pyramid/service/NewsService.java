package com.financial.pyramid.service;

import com.financial.pyramid.domain.News;
import com.financial.pyramid.web.form.QueryForm;

import java.util.List;

/**
 * User: dbudunov
 * Date: 21.06.13
 * Time: 19:54
 */
public interface NewsService {
    public List<News> findByQuery(QueryForm form);
    public News findById(Long id);
    public List<News> find();
    public void save(News news);
    public void remove(Long id);
}
