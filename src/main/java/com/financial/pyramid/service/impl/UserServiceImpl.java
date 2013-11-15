package com.financial.pyramid.service.impl;

import com.financial.pyramid.dao.UserDao;
import com.financial.pyramid.domain.Account;
import com.financial.pyramid.domain.Passport;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.service.AccountService;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.service.beans.AccountDetails;
import com.financial.pyramid.settings.Setting;
import com.financial.pyramid.utils.Session;
import com.financial.pyramid.web.form.AdminRegistrationForm;
import com.financial.pyramid.web.form.QueryForm;
import com.financial.pyramid.web.tree.BinaryTree;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: Danil
 * Date: 29.05.13
 * Time: 22:17
 */
@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final static Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    SettingsService settingsService;

    @Autowired
    AccountService accountService;

    @Override
    @Transactional(readOnly = false)
    public void save(User user) {
        userDao.saveOrUpdate(user);
    }

    @Override
    @Transactional(readOnly = false)
    public User merge(User user) {
        return userDao.merge(user);
    }

    @Override
    @Transactional(readOnly = false)
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public void delete(Long id) {
        User user = userDao.findById(id);
        if (user.getLeftChild() != null && user.getRightChild() != null) {
            return;
        }
        if (user.getLeftChild() == null && user.getRightChild() == null) {
            userDao.delete(user);
            return;
        }
        User parent = user.getParent();
        User child = user.getLeftChild() != null ? user.getLeftChild() : user.getRightChild();
        child.setParent(parent);
        if (user.getLeftChild() != null && user.getLeftChild().getId().equals(id)) {
            parent.setLeftChild(child);
        } else {
            parent.setRightChild(child);
        }
        updateUserPosition(parent, parent.getLevel(), parent.getUri());
        save(parent);
        userDao.delete(user);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public boolean checkEmail(String email) {
        return userDao.isEmail(email);
    }

    @Override
    public List<User> list() {
        return userDao.findAll();
    }

    @Override
    public List<User> findByQuery(QueryForm form) {
        return userDao.findByQuery(form);
    }

    @Override
    @Transactional(readOnly = false)
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public void update(AdminRegistrationForm form) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        User user = findById(form.getId());
        user.setName(form.getName());
        user.setSurname(form.getSurname());
        user.setPatronymic(form.getPatronymic());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setEmail(form.getEmail());
        try {
            Date date = format.parse(form.getDateOfBirth());
            user.setDateOfBirth(date);
        } catch (ParseException e) {
            logger.info("User date of birth is not set");
        }

        Passport passport = user.getPassport();
        passport.setSerial(form.getPassportSerial());
        passport.setNumber(form.getPassportNumber());
        passport.setIssuedBy(form.getPassportIssuedBy());
        passport.setRegisteredAddress(form.getRegisteredAddress());
        passport.setResidenceAddress(form.getResidenceAddress());

        try {
            Date date = format.parse(form.getPassportDate());
            passport.setDate(date);
        } catch (ParseException e) {
            logger.info("User passport date is not set");
        }
        user.setPassport(passport);
        userDao.saveOrUpdate(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority(user.getRole().name())));
        } else {
            throw new UsernameNotFoundException("User with name " + username + " not found");
        }
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public BinaryTree getBinaryTree(User u) {
        Integer levels = Integer.parseInt(settingsService.getProperty(Setting.COUNT_LEVEL_IN_USER_TREE));
        Integer details = Integer.parseInt(settingsService.getProperty(Setting.COUNT_LEVEL_FOR_USER_DETAILS));
        User user = findById(u.getId());

        Authentication authentication = Session.getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            User topUser = (User) authentication.getDetails();
            if (!u.getId().equals(topUser.getId())) {
                Integer differentInLevels = u.getLevel() - topUser.getLevel();
                details = differentInLevels < 0 ? 0 : details - differentInLevels;
            }
        }

        return new BinaryTree(user, levels, details);
    }

    @Override
    public List<User> findUsersByOwner(Long ownerId) {
        return userDao.findByOwner(ownerId);
    }

    @Override
    public AccountDetails getAccountDetails(User u) {
        User user = findById(u.getId());
        AccountDetails accountDetails = new AccountDetails();

        Date activationStartDate = user.getAccount().getDateActivated();
        Date activationEndDate = user.getAccount().getDateExpired();

        accountDetails.setDateExpired(activationEndDate);
        accountDetails.setDateActivated(activationStartDate);

        Double balance = user.getAccount().getBalance();
        accountDetails.setBalance(new BigDecimal(balance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

        if (activationStartDate != null) {
            accountDetails.setDaysMonth(Days.daysBetween(new DateTime(activationStartDate),
                    new DateTime(activationEndDate).minusDays(1)).getDays());
        }

        if (activationEndDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            accountDetails.setDaysLeft(Days.daysBetween(new DateTime(calendar.getTime()), new DateTime(activationEndDate)).getDays());
        }

        accountDetails.setLocked(user.getAccount().isLocked());
        accountDetails.setAppPaid(user.getAccount().isAppPaid());
        return accountDetails;
    }

    @Override
    public Account getAccount(User u) {
        User user = findById(u.getId());
        return user.getAccount();
    }

    @Override
    @Transactional(readOnly = false)
    public void activateUserAccount(User user, int monthsPayed) {
        Account account = getAccount(user);
        accountService.activate(account, monthsPayed);
    }

    @Override
    public Long getCountUsersOnLevel(Integer level) {
        return userDao.getCountUsersOnLevel(level);
    }

    @Override
    public User findParent(Long userId) {
        return userDao.findParent(userId);
    }

    @Override
    @Transactional(readOnly = false)
    public void withdrawFromAccount(User user, Double count) {
        User u = findById(user.getId());
        if (u == null) return;
        u.getAccount().writeOFF(count, "withdraw_funds_from_account");
        merge(user);
    }

    private void updateUserPosition(User user, Integer level, String uri) {
        user.setLevel(level);
        user.setUri(uri);
        if (user.getLeftChild() != null) updateUserPosition(user.getLeftChild(), level + 1, uri+"1");
        if (user.getRightChild() != null) updateUserPosition(user.getRightChild(), level + 1, uri+"2");
    }
}
