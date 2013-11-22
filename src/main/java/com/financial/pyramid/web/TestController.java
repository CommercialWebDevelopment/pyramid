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
    private PasswordEncoder passwordEncoder;

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
        if(levels == 1) return "Done";

        System.out.println("Started generator...");
        long timePoint = new Date().getTime();

        User admin = userService.findById(1L); // Admin
        Integer totalCount = ((Double)Math.pow(2, levels)).intValue() - 1; // количество пользователей
        Queue<User> users = generateUsers(totalCount - 1);   // -1 admin
        Queue<User> parents = new ArrayBlockingQueue<User>(totalCount - 1);
        parents.add(admin);

        while (!users.isEmpty()) {
            List<User> usersWithoutParents = new ArrayList<User>(parents.size() * 2);
            while (!parents.isEmpty()) {
                User parent = parents.poll();
                User left = users.poll();
                User right = users.poll();
                if (left != null) {
                    parent.setLeftChild(left);
                    usersWithoutParents.add(left);
                }
                if (right != null) {
                    parent.setRightChild(right);
                    usersWithoutParents.add(right);
                }
            }
            parents.addAll(usersWithoutParents);
        }

        // сохраняем с выставлением уровней
        save(admin, 0, "1");
        System.out.println("Duration is " + (new Date().getTime() - timePoint) + " milliseconds");
        return "Done";
    }

    private User save(User user, int level, String uri) {
        if (user.getLeftChild() != null) user.setLeftChild(save(user.getLeftChild(), level + 1, uri+"1"));
        if (user.getRightChild() != null) user.setRightChild(save(user.getRightChild(), level + 1,uri+"2"));
        user.setLevel(level);
        user.setUri(uri);
        return userService.merge(user);
    }

    private Queue<User> generateUsers(Integer users) {
        Queue<User> userQueue = new ArrayBlockingQueue<User>(users);

        for (int i = 0; i < users; i++) {
            User user = new User();
            user.setName("TestUser_" + i);
            user.setSurname("TestUser_" + i);
            user.setPatronymic("TestUser_" + i);
            user.setPhoneNumber(String.valueOf(8917702200L + i));
            user.setEmail("test_" + i + "@test.com");
            user.setRole(Role.USER);
            user.setOwnerId(1L);
            user.setDateOfBirth(new Date());
            user.setPassword(passwordEncoder.encode("qwerty"));

            Account account = new Account();
            Calendar calendar = Calendar.getInstance();
            account.setDateActivated(calendar.getTime());
            calendar.add(Calendar.MONTH, -1);
            account.setDateExpired(calendar.getTime());
            account.writeIN(1D, "test_account_created", null);
            user.setAccount(account);
            userQueue.add(user);
        }
        return userQueue;
    }
}
