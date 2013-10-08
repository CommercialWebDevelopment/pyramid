package com.financial.pyramid.web;

import com.financial.pyramid.domain.Account;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.domain.type.Role;
import com.financial.pyramid.service.AccountService;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.utils.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: dbudunov
 * Date: 29.08.13
 * Time: 14:12
 */
@Controller
@RequestMapping("/test")
public class TestController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SettingsService settingsService;

    @ResponseBody
    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public String payTest(ModelMap model) {
        User currentUser = Session.getCurrentUser();
        Account account = userService.getAccount(currentUser);
        accountService.calculateSum(account, 0.01);
        return "Done";
    }

    @ResponseBody
    @RequestMapping(value = "/extend", method = RequestMethod.GET)
    public String extendTest(ModelMap model) {
        User currentUser = Session.getCurrentUser();
        Account account = userService.getAccount(currentUser);
        accountService.activate(account, 1);
        return "Done";
    }

    @ResponseBody
    @RequestMapping(value = "/block", method = RequestMethod.GET)
    public String blockTest(ModelMap model) {
        User currentUser = Session.getCurrentUser();
        Account account = userService.getAccount(currentUser);
        accountService.deactivate(account);
        return "Done";
    }

    @ResponseBody
    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public String generateTree(ModelMap model) {
        System.out.println("Started generator...");
        User parentUser = userService.findById(1L);
        Map<Integer, User> users = new HashMap<Integer, User>();
        users.put(0, parentUser);
        int counter = 0;
        for (int i = 1; i <= 1000; i++) {
            for (int j = 0; j < i * 2; j++) {
                long timePoint = new Date().getTime();
                User currentUser = new User();
                currentUser.setName("TestUserName_" + i + "_" + j);
                currentUser.setSurname("TestUserSurname_" + i + "_" + j);
                currentUser.setPatronymic("TestUserPatronymic_" + i + "_" + j);
                currentUser.setPhoneNumber(String.valueOf(8917702200L + i));
                currentUser.setEmail("test_" + i + "_" + j + "@test.com");
                currentUser.setRole(Role.USER);
                currentUser.setOwnerId(1L);
                currentUser.setLevel(i);
                DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT, LocaleContextHolder.getLocale());
                try {
                    currentUser.setDateOfBirth(format.parse("01.01.1980"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                currentUser.setPassword(passwordEncoder.encode("qwerty"));

                Account account = new Account();
                Calendar calendar = Calendar.getInstance();
                account.setDateActivated(calendar.getTime());
                calendar.add(Calendar.MONTH, -1);
                account.setDateExpired(calendar.getTime());
                account.writeIN(0D);

                currentUser.setAccount(account);
                userService.save(currentUser);
                System.out.println("New user [" + currentUser.getShortName() + "] created");
                User createdUser = userService.findByEmail(currentUser.getEmail());
                if (parentUser.getLeftChild() == null) {
                    parentUser.setLeftChild(createdUser);
                } else if (parentUser.getRightChild() == null) {
                    parentUser.setRightChild(createdUser);
                }
                counter++;
                userService.save(parentUser);
                System.out.println("Parent user [" + parentUser.getShortName() + "] updated");
                users.put(counter, createdUser);
                int previousParent = (int) (counter - 1) / 2;
                parentUser = userService.findById(users.get(previousParent).getId());
                System.out.println("Duration is " + (new Date().getTime() - timePoint) + " milliseconds");
            }
            System.out.println("Level " + i + " is completed");
        }
        return "Done";
    }
}
