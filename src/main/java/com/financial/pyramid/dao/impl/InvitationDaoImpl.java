package com.financial.pyramid.dao.impl;

import com.financial.pyramid.dao.InvitationDao;
import com.financial.pyramid.domain.Invitation;
import com.financial.pyramid.domain.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Danil
 * Date: 12.08.13
 * Time: 22:07
 */

@Repository(value = "invitationDao")
public class InvitationDaoImpl extends AbstractDaoImpl<Invitation, Long> implements InvitationDao {

    protected InvitationDaoImpl() {
        super(Invitation.class);
    }

    @Override
    public Invitation findByEmail(String email) {
        List<Invitation> invitations = findByCriteria(Restrictions.eq("email", email));
        return invitations.size() == 0 ? null : invitations.get(0);
    }

    @Override
    public Invitation findByGlobalId(String globalId) {
        List<Invitation> invitations = findByCriteria(Restrictions.eq("globalId", globalId));
        assert invitations.size() > 1;
        return invitations.size() > 0 ? invitations.get(0) : null;
    }
}
