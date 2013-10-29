package com.financial.pyramid.web;

import com.financial.pyramid.domain.Contact;
import com.financial.pyramid.domain.News;
import com.financial.pyramid.domain.User;
import com.financial.pyramid.domain.Video;
import com.financial.pyramid.service.*;
import com.financial.pyramid.service.beans.AccountDetails;
import com.financial.pyramid.settings.Setting;
import com.financial.pyramid.utils.Session;
import com.financial.pyramid.web.form.AuthenticationForm;
import com.financial.pyramid.web.form.InvitationForm;
import com.financial.pyramid.web.form.PageForm;
import com.financial.pyramid.web.form.QueryForm;
import com.financial.pyramid.web.tree.BinaryTree;
import com.financial.pyramid.web.tree.BinaryTreeWidget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * User: Danil
 * Date: 05.06.13
 * Time: 23:03
 */
@Controller
@RequestMapping("/app")
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
        String youTubeUrl = settingsService.getProperty(Setting.YOU_TUBE_VIDEO_URL);
        model.addAttribute("youTubeUrl", youTubeUrl);
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
        Authentication authentication = Session.getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            User user = (User) authentication.getDetails();
            AccountDetails accountDetails = userService.getAccountDetails(user);
            model.addAttribute("dateExpired", localizationService.formatDate(accountDetails.getDateExpired()));
            model.addAttribute("dateActivated", localizationService.formatDate(accountDetails.getDateActivated()));
            model.addAttribute("currencySign", settingsService.getProperty(Setting.CASH_SIGN));
            model.addAttribute("daysLeft", accountDetails.getDaysLeft() != null ? accountDetails.getDaysLeft() : -1);
            model.addAttribute("monthDays", accountDetails.getDaysMonth() != null ? accountDetails.getDaysMonth() : -1);
            model.addAttribute("balance", accountDetails.getBalance());
            model.addAttribute("invitation", new InvitationForm());
            model.addAttribute("isAppPaid", accountDetails.isAppPaid());
            return "/tabs/user/private-office";
        }
        model.addAttribute("authentication", new AuthenticationForm());
        return "/tabs/login";
    }

    @ResponseBody
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String tree(ModelMap model) {
        Authentication authentication = Session.getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            User user = (User) authentication.getDetails();
            AccountDetails accountDetails = userService.getAccountDetails(user);
            BinaryTree tree = userService.getBinaryTree(user);
            BinaryTreeWidget widget = new BinaryTreeWidget();
            widget.setStubText(localizationService.translate("user.add"), localizationService.translate("user.add.details"));
            widget.setStatus(localizationService.translate("activeUser"), localizationService.translate("inactiveUser"));
            widget.setAddEnabled(!accountDetails.isLocked());
//            widget.initTree(tree);
            double treeWidth = Math.pow(2, tree.getDepth()) * 50;
            String treeCanvas = "<link rel='stylesheet' href='/resources/css/tree.css' type='text/css'>";
            treeCanvas += "<div class='tree'>";
            treeCanvas += "<ul><li style='width:" + treeWidth + "px'><a href='#'>" + tree.getValue().getName() + " " + tree.getValue().getSurname() + "</a>";
            while (tree != null) {
                treeCanvas += "<ul>";
                if (tree.isLeft()) {
                    treeCanvas += "<li style='width:" + (treeWidth / Math.pow(2, tree.getLeft().getLevel())) + "px'><a href='#'>" + tree.getLeft().getValue().getName() + " " + tree.getLeft().getValue().getSurname() + "</a>";
                } else {
                    treeCanvas += "<li style='width:" + (treeWidth / Math.pow(2, tree.getLevel())) + "px'><a href='#'>+</a></li>";
                }
                if (tree.isRight()) {
                    treeCanvas += "<li style='width:" + (treeWidth / Math.pow(2, tree.getRight().getLevel())) + "px'><a href='#'>" + tree.getRight().getValue().getName() + " " + tree.getRight().getValue().getSurname() + "</a>";
                } else {
                    treeCanvas += "<li style='width:" + (treeWidth / Math.pow(2, tree.getLevel())) + "px'><a href='#'>+</a></li>";
                }
                if (tree.isChild()) {
                    tree = tree.getLeft() != null ? tree.getLeft() : tree.getRight();
                } else {
                    while (tree.itIsRight() || (tree.isParent() && !tree.getParent().isRight())) {
                        tree = tree.getParent();
                    }
                    if (tree.isParent()) {
                        tree = tree.getParent().getRight();
                        treeCanvas += "</li>";
                    } else {
                        tree = null;
                        treeCanvas += "</li></ul>";
                    }
                }
            }
            treeCanvas += "</li></ul>";
            return treeCanvas;
        }
        return "";
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

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String redirect(ModelMap modelMap, @RequestParam(value = "to") String destination) {
        String url = settingsService.getProperty(destination);
        return "redirect:" + url;
    }
}
