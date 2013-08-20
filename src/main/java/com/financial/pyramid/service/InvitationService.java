package com.financial.pyramid.service;

import com.financial.pyramid.domain.Invitation;
import com.financial.pyramid.service.exception.InvitationNotFoundException;
import com.financial.pyramid.service.exception.InvitationOverdueException;
import com.financial.pyramid.web.form.InvitationForm;

import java.util.List;

/**
 * User: Danil
 * Date: 12.08.13
 * Time: 22:16
 */
public interface InvitationService {

    public List<Invitation> findByEmail(String email);

    public Invitation confirm(String globalId) throws InvitationNotFoundException, InvitationOverdueException;

    public Invitation findByGlobalId(String globalId);

    public boolean sendInvitation(InvitationForm invitationForm);

    public Invitation findById(Long id);
}
