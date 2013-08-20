package com.financial.pyramid.dao;

import com.financial.pyramid.domain.Invitation;

import java.util.List;

/**
 * User: Danil
 * Date: 12.08.13
 * Time: 22:06
 */
public interface InvitationDao extends AbstractDao<Invitation, Long> {

    public List<Invitation> findByEmail(String email);

    public Invitation findByGlobalId(String globalId);
}
