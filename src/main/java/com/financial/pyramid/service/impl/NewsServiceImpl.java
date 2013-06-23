package com.financial.pyramid.service.impl;

import com.financial.pyramid.dao.NewsDao;
import com.financial.pyramid.domain.News;
import com.financial.pyramid.service.NewsService;
import com.financial.pyramid.web.form.QueryForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: dbudunov
 * Date: 21.06.13
 * Time: 19:55
 */
@Service("newsService")
@Transactional
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsDao newsDao;

    @Override
    public List<News> findByQuery(QueryForm form){
        return newsDao.findByQuery(form);
    }

    @Override
    public List<News> find() {
        return newsDao.findAll();
    }

    @Override
    public void save(News news) {
        newsDao.saveOrUpdate(news);
    }

    @Override
    public void remove(Long id) {
        News news = newsDao.findById(id);
        newsDao.delete(news);
    }
}
