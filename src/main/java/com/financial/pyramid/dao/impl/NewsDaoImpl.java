package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.NewsDao;
import com.financial.pyramid.domain.News;
import com.financial.pyramid.web.form.QueryForm;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: dbudunov
 * Date: 21.06.13
 * Time: 19:52
 */
@Repository(value = "newsDao")
public class NewsDaoImpl extends AbstractDaoImpl<News, Long> implements NewsDao {
    protected NewsDaoImpl() {
        super(News.class);
    }

    public List<News> findByQuery(QueryForm form){
        Criteria criteria = getCurrentSession().createCriteria(News.class);
        criteria.setCacheable(true)
                .addOrder(form.order.equals("asc") ? Order.asc(form.sort) : Order.desc(form.sort))
                .setFirstResult((form.page - 1) * form.count + 1)
                .setMaxResults(form.count);
        return (List<News>) criteria.list();
    }
}
