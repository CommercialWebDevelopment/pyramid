package com.financial.pyramid.service.impl;

import com.financial.pyramid.dao.InvitationDao;
import com.financial.pyramid.domain.Invitation;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.domain.type.Position;
import com.financial.pyramid.service.EmailService;
import com.financial.pyramid.service.InvitationService;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.service.exception.InvitationNotFoundException;
import com.financial.pyramid.service.exception.InvitationOverdueException;
import com.financial.pyramid.utils.Session;
import com.financial.pyramid.web.form.InvitationForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Danil
 * Date: 13.08.13
 * Time: 20:18
 */
@Service("invitationService")
@Transactional(readOnly = true)
public class InvitationServiceImpl implements InvitationService {
    public static final int CONFIRM_PERIOD = 604800000;

    private final static Logger logger = Logger.getLogger(InvitationServiceImpl.class);

    @Autowired
    InvitationDao invitationDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public Invitation findByEmail(String email) {
        return invitationDao.findByEmail(email);
    }

    @Override
    public Invitation confirm(String globalId) throws InvitationNotFoundException, InvitationOverdueException {
        Invitation invitation = findByGlobalId(globalId);
        if (invitation == null) throw new InvitationNotFoundException();
        if (invitation.getConfirmed()) return invitation;
        if ((System.currentTimeMillis() - invitation.getCreated().getTime()) > CONFIRM_PERIOD)
            throw new InvitationOverdueException();
        invitation.setConfirmed(true);
        logger.info("Invitation with id = " + invitation.getId() + " was confirmed.");
        return invitationDao.merge(invitation);
    }

    @Override
    public Invitation findByGlobalId(String globalId) {
        return invitationDao.findByGlobalId(globalId);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean sendInvitation(InvitationForm invitationForm) {
        Object principal = Session.getAuthentication().getPrincipal();
        User sender = userService.findByEmail(((UserDetails) principal).getUsername());
        User parent;
        if (invitationForm.getParentId().equals(sender.getId())) {
            parent = sender;
        } else {
            parent = userService.findById(invitationForm.getParentId());
        }

        Invitation invitation = new Invitation();
        invitation.setConfirmed(false);
        invitation.setEmail(invitationForm.getEmail());
        invitation.setGlobalId(passwordEncoder.encode(invitationForm.getEmail()));
        invitation.setParent(parent);
        invitation.setPosition(Position.valueOf(invitationForm.getPosition()));
        invitation.setSender(sender);

        if (emailService.sendInvitation(invitation.getGlobalId(), sender.getName(), invitation.getEmail())) {
            invitationDao.saveOrUpdate(invitation);
            logger.info("Invitation created. User: " + invitation.getEmail() + " Sender: " + sender.getId() + " Owner: " + parent.getId());
            return true;
        }
        return false;
    }

    @Override
    public Invitation findById(Long id) {
        return invitationDao.findById(id);
    }

    @Override
    public void delete(Invitation invitation) {
        invitationDao.delete(invitation);
    }
}
