package com.financial.pyramid.web;

import com.financial.pyramid.domain.Contact;
import com.financial.pyramid.domain.News;
import com.financial.pyramid.domain.Video;
import com.financial.pyramid.service.*;
import com.financial.pyramid.settings.Setting;
import com.financial.pyramid.web.form.*;
import com.financial.pyramid.web.tree.BinaryTree;
import com.financial.pyramid.web.tree.BinaryTreeWidget;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * User: Danil
 * Date: 05.06.13
 * Time: 23:03
 */
@Controller
@RequestMapping("/pyramid")
public class TabsController extends AbstractController {

    @Autowired
    protected VideoService videoService;

    @Autowired
    protected NewsService newsService;

    @Autowired
    protected SettingsService settingsService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected ContactService contactService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(ModelMap model) {
        return "/tabs/home";
    }

    @RequestMapping(value = "/training", method = RequestMethod.GET)
    public String training(ModelMap model) {
        List<Video> videos = videoService.find();
        Video video = videos != null && videos.size() > 0 ? videos.get(0) : null;
        String defaultVideo = settingsService.getProperty(Setting.YOU_TUBE_VIDEO_URL, video != null ? video.getExternalId() : null);
        model.addAttribute("defaultVideo", defaultVideo);
        model.addAttribute("videos", videos);
        return "/tabs/training";
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String news(ModelMap model, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        PageForm<News> newsForm = new PageForm<News>();
        QueryForm form = new QueryForm();
        form.setSort("date");
        form.setOrder("desc");
        form.setPage(page);
        int total = newsService.find().size();
        List<News> list = newsService.findByQuery(form);
        newsForm.setPage(page);
        newsForm.setRows(list);
        newsForm.setTotal(total);
        model.addAttribute("newsForm", newsForm);
        return "/tabs/news";
    }

    @RequestMapping(value = "/office", method = RequestMethod.GET)
    public String office(ModelMap model, HttpSession session, HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            BinaryTree tree = userService.getBinaryTree(((UserDetails) principal).getUsername());
            BinaryTreeWidget widget = new BinaryTreeWidget(tree);
            widget.setStubText(getMessage("user.add"));

            while (tree != null) {
                widget.addUserToWidget(tree);

                if (tree.isChild()) {
                    tree = tree.getLeft() != null ? tree.getLeft() : tree.getRight();
                } else {
                    while (tree.itIsRight() || (tree.isParent() && !tree.getParent().isRight())) {
                        tree = tree.getParent();
                    }
                    tree = tree.isParent() ? tree.getParent().getRight() : null;
                }
            }
            Date activationEndDate = new Date("2013/09/30");
            int daysLeft = Days.daysBetween(new DateTime(), new DateTime(activationEndDate)).getDays();
            double earningsSum = new BigDecimal(3000).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Calendar c = Calendar.getInstance();
            int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            model.addAttribute("daysLeft", daysLeft);
            model.addAttribute("monthDays", monthMaxDays);
            model.addAttribute("earningsSum", earningsSum);
            model.addAttribute("userBinaryTree", widget.getRootElement());
            model.addAttribute("invitation", new InvitationForm());
            return "/tabs/user/private-office";
        }
        model.addAttribute("authentication", new AuthenticationForm());
        return "/tabs/login";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(ModelMap model) {
        return "/tabs/about";
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String contacts(ModelMap model) {
        List<Contact> contacts = contactService.findAll();
        String address = settingsService.getProperty(Setting.CONTACTS_ADDRESS);
        String phones = settingsService.getProperty(Setting.CONTACTS_PHONES);
        String mapUrl = settingsService.getProperty(Setting.CONTACTS_MAP_URL);
        String fullMapUrl = settingsService.getProperty(Setting.CONTACTS_MAP_HREF);
        model.addAttribute("address", address);
        model.addAttribute("phones", phones);
        model.addAttribute("mapUrl", mapUrl);
        model.addAttribute("showFullMapUrl", fullMapUrl);
        model.addAttribute("contacts", contacts);
        return "/tabs/contacts";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(ModelMap model, HttpSession session, HttpServletRequest request) {
        model.addAttribute("registration", new RegistrationForm());
        return "tabs/user/registration-form";
    }
}
