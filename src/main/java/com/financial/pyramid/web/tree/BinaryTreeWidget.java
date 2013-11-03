package com.financial.pyramid.web.tree;

import com.financial.pyramid.domain.type.Position;
import com.financial.pyramid.web.form.UserForm;

/**
 * User: Danil
 * Date: 10.08.13
 * Time: 1:27
 */
public class BinaryTreeWidget {
    private static String LEFT_TREE = "left-tree";
    private static String RIGHT_TREE = "right-tree";
    private String rootElement;
    private double rootElementWidth = 0;
    private String stubTextTitle;
    private String stubTextContent;
    private String activeStatus;
    private String inactiveStatus;
    private static final short DEFAULT_CANVAS_HEIGHT = 30;
    private static final short MAX_CANVAS_WIDTH = 30000;
    private int depth = 1;
    private static int STUB_WITH = 48;

    public void initTree(BinaryTree tree) {
        if (tree == null) {
            this.rootElement = "";
            return;
        }
        this.rootElementWidth = calculateTotalWidth(tree);
        this.rootElement = "<div class='tree' style='width:" + this.rootElementWidth + "px'><ul>" + getUserNode(tree) + "</ul></div>";
    }

    private double calculateTotalWidth(BinaryTree tree) {
        this.depth = tree.getDepth();
        double countUsers = Math.pow(2, this.depth);
        return (STUB_WITH + 10) + (countUsers - 1) * (STUB_WITH + 20);
    }

    private double calculateNodeWidth(Integer depth) {
        double countUsers = Math.pow(2, depth);
        return STUB_WITH + (countUsers - 1) * (STUB_WITH + 20);  // padding
    }

    public String getRootElement() {
        return rootElement;
    }

    public String getUserNode(BinaryTree tree) {
        UserForm user = tree.getValue();

        String statusText = user.isActive() ? this.activeStatus : this.inactiveStatus;
        String statusColor = user.isActive() ? "green" : "red";
        String popupTitle = " '<div class=user-name><b>" + user.getName() + " " + user.getSurname() + "</b></div>' ";
        String popupContent;

        String photo = user.getPhoto();
        String imgDir = "/resources/images/";
        if (photo == null) {
            photo = imgDir + (user.isActive() ? "vcard-active" : "vcard-inactive") + ".png";
        }
        String body = "<img src=" + photo + " class=user-photo ";
        if (!user.isShowDetails()) {
            popupContent = " '<small><div style=color:" + statusColor + ">" + statusText + "</div></small>' ";
            body += "data-content=" + popupContent + "/>";
        } else {
            popupContent = " '<small><div>" + user.getEmail() + "</div><div style=color:" + statusColor + ">" + statusText + "</div></small>' ";
            body += "title=" + popupTitle + " data-content=" + popupContent + "/></br>";
            body += "<div class='user-name'>" + user.getName() + " " + user.getSurname() + "</div>";
        }
        boolean isChild = tree.isLeft() || tree.isRight();
        String childPlace = tree.getValue().isActive() || isChild  ? "<ul>" + getPointForUser(tree) + "</ul>" : "";
        return "<li style='width:" + calculateNodeWidth(tree.getDepth()) + "px'>" + body + childPlace + "</li>";
    }

    public String getStubNode(BinaryTree tree, String clazz) {
        if(!tree.getValue().isActive()) return "<div style=width:" + calculateNodeWidth(tree.getDepth() - 1) + "px></div>";
        String image = "<img class=stub-node parentId=" + tree.getId();
        image += " position=" + (clazz.equals(RIGHT_TREE) ? Position.RIGHT.toString() : Position.LEFT.toString());
        image += " src=/resources/images/add-user.jpg title='" + this.stubTextTitle + "' data-content='" + this.stubTextContent + "'/>";
        return "<li style=width:" + calculateNodeWidth(tree.getDepth() - 1) + "px>" + image + "</li>";
    }

    public String getPointForUser(BinaryTree user) {
        String point = "I";
        return point + user.getId() + point;
    }

    public void addUserToWidget(BinaryTree user) {
        String result = user.isLeft() ? getUserNode(user.getLeft()) : getStubNode(user, LEFT_TREE);
        result += user.isRight() ? getUserNode(user.getRight()) : getStubNode(user, RIGHT_TREE);
        rootElement = rootElement.replaceFirst(getPointForUser(user), result);
    }

    public void setStubText(String stubTextTitle, String stubTextContent) {
        this.stubTextTitle = stubTextTitle;
        this.stubTextContent = "<small>" + stubTextContent + "</small>";
    }

    public void setStatus(String activeStatus, String inactiveStatus) {
        this.activeStatus = activeStatus;
        this.inactiveStatus = inactiveStatus;
    }
}
