package com.financial.pyramid.web;

import com.financial.pyramid.domain.Account;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.domain.type.Role;
import com.financial.pyramid.service.AccountService;
import com.financial.pyramid.service.SettingsService;
import com.financial.pyramid.service.UserService;
import com.financial.pyramid.utils.Session;
import com.financial.pyramid.web.tree.BinaryTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

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
    @RequestMapping(value = "/payAccountTest", method = RequestMethod.GET)
    public String payTest(ModelMap model) {
        User currentUser = Session.getCurrentUser();
        Account account = userService.getAccount(currentUser);
        accountService.calculateSum(account, 0.01);
        return "Done";
    }

    @ResponseBody
    @RequestMapping(value = "/extendAccountTest", method = RequestMethod.GET)
    public String extendTest(ModelMap model) {
        User currentUser = Session.getCurrentUser();
        Account account = userService.getAccount(currentUser);
        accountService.activate(account, 1);
        return "Done";
    }

    @ResponseBody
    @RequestMapping(value = "/blockAccountTest", method = RequestMethod.GET)
    public String blockTest(ModelMap model) {
        User currentUser = Session.getCurrentUser();
        Account account = userService.getAccount(currentUser);
        accountService.deactivate(account);
        return "Done";
    }

    @ResponseBody
    @RequestMapping(value = "/generateTreeTest/{levels}", method = RequestMethod.GET)
    public String generateTreeTest(ModelMap model, @PathVariable int levels) {
        System.out.println("Started generator...");
        User parentUser = userService.findById(1L);
        Map<Integer, User> users = new HashMap<Integer, User>();
        users.put(0, parentUser);
        int counter = 0;
        int previousLevelCount = 1;
        for (int i = 1; i <= levels; i++) {
            for (int j = 0; j < previousLevelCount * 2; j++) {
                long timePoint = new Date().getTime();
                User currentUser = new User();
                currentUser.setName("TestUser_" + i + "_" + j);
                currentUser.setSurname("TestUser_" + i + "_" + j);
                currentUser.setPatronymic("TestUser_" + i + "_" + j);
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
                account.writeIN(1D);
                currentUser.setAccount(account);
                userService.save(currentUser);

                System.out.println("New user [" + currentUser.getShortName() + "] created");
                User createdUser = userService.findByEmail(currentUser.getEmail());
                if (parentUser.getLeftChild() == null) {
                    parentUser.setLeftChild(createdUser);
                } else if (parentUser.getRightChild() == null) {
                    parentUser.setRightChild(createdUser);
                }
                parentUser.setCountInvitedUsers(parentUser.getCountInvitedUsers()+1);
                userService.save(parentUser);
                counter++;

                System.out.println("Parent user [" + parentUser.getShortName() + "] updated");
                users.put(counter, createdUser);
                int previousParent = (int) (counter - 1) / 2;
                parentUser = userService.findById(users.get(previousParent).getId());
                System.out.println("Duration is " + (new Date().getTime() - timePoint) + " milliseconds");
            }
            previousLevelCount = previousLevelCount * 2;
            System.out.println("Level " + i + " is completed");
        }
        return "Done";
    }

    @ResponseBody
    @RequestMapping(value = "/countUsersTest/{userId}", method = RequestMethod.GET)
    public String countUsersTest(ModelMap model, @PathVariable long userId) {
        User user = userService.findById(userId);
        BinaryTree tree = userService.getBinaryTree(user);
        int counter = 0;
        while (tree != null) {
            counter++;
            if (tree.isChild()) {
                tree = tree.getLeft() != null ? tree.getLeft() : tree.getRight();
            } else {
                while (tree.itIsRight() || (tree.isParent() && !tree.getParent().isRight())) {
                    tree = tree.getParent();
                }
                tree = tree.isParent() ? tree.getParent().getRight() : null;
            }
        }
        return String.valueOf(counter);
    }

    @ResponseBody
    @RequestMapping(value = "/generateTree/{levels}", method = RequestMethod.GET)
    public String generateTree(@PathVariable int levels) {
        System.out.println("Started generator...");
        long timePoint = new Date().getTime();

        User admin = userService.findById(1L); // Admin
        Integer totalCount = (2 - 2*((Double)Math.pow(2, levels)).intValue()) / -1; // количество пользователей
        Queue<User> userWithoutParent = generateUsers(totalCount, admin);

        while (!userWithoutParent.isEmpty()) {
            User parent = userWithoutParent.poll();
            User left = userWithoutParent.poll();
            User right = userWithoutParent.poll();
            if (left != null) {
                if (left.getId() == null) left = userService.merge(left);
                parent.setLeftChild(left);
                userWithoutParent.add(left);
            }
            if (right != null) {
                if (right.getId() == null) right = userService.merge(right);
                parent.setRightChild(right);
                userWithoutParent.add(right);
            }
            userService.merge(parent);
        }

        System.out.println("Duration is " + (new Date().getTime() - timePoint) + " milliseconds");
        return "Done";
    }

    private Queue<User> generateUsers(Integer users, User first) {
        Queue<User> userQueue = new ArrayBlockingQueue<User>(users + 1);
        userQueue.add(first);

        for (int i = 0; i < users; i++) {
            User user = new User();
            user.setName("TestUser_" + i);
            user.setSurname("TestUser_" + i);
            user.setPatronymic("TestUser_" + i);
            user.setPhoneNumber(String.valueOf(8917702200L + i));
            user.setEmail("test_" + i + "@test.com");
            user.setRole(Role.USER);
            user.setOwnerId(1L);
            user.setLevel(i);
            user.setDateOfBirth(new Date());
            user.setPassword(passwordEncoder.encode("qwerty"));

            Account account = new Account();
            Calendar calendar = Calendar.getInstance();
            account.setDateActivated(calendar.getTime());
            calendar.add(Calendar.MONTH, -1);
            account.setDateExpired(calendar.getTime());
            account.writeIN(1D);
            user.setAccount(account);
            userQueue.add(user);
        }
        return userQueue;
    }
}
