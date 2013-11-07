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
    private int depth = 1;
    private static int STUB_WIDTH_HUGE = 48;
    private static int STUB_WIDTH_SMALL = 20;
    private String mode;
    private boolean active;

    public void initTree(BinaryTree tree, String mode) {
        if (tree == null) {
            this.rootElement = "";
            return;
        }
        this.mode = mode != null ? mode : "huge";
        this.rootElementWidth = calculateTotalWidth(tree);
        this.rootElement = "<div id='tree' class='tree' style='width:" + this.rootElementWidth + "px' userId='"+tree.getId()+"'><ul>" + getUserNode(tree) + "</ul></div>";
    }

    private double calculateTotalWidth(BinaryTree tree) {
        this.depth = tree.getDepth();
        double countUsers = Math.pow(2, this.depth);
        int stubWidth = isStandardView() ? STUB_WIDTH_HUGE : 0;
        return (stubWidth + 10) + (countUsers - 1) * (stubWidth + 20);
    }

    private double calculateNodeWidth(Integer level) {
        double countUsers = Math.pow(2, (this.depth - level));
        int stubWidth = isStandardView() ? STUB_WIDTH_HUGE : 0;
        return stubWidth + (countUsers - 1) * (stubWidth + 20);  // padding
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
        int iconWidth = tree.getLevel() == 0 ? STUB_WIDTH_HUGE : getIconWidth();
        String body = "<img src=" + photo + " class=user-photo width='" + iconWidth + "px' userId='"+tree.getId()+"'";
        if (!user.isShowDetails()) {
            popupContent = " '<small><div style=color:" + statusColor + ">" + statusText + "</div></small>' ";
            body += "data-content=" + popupContent + "/>";
        } else {
            popupContent = " '<small><div>" + user.getEmail() + "</div><div style=color:" + statusColor + ">" + statusText + "</div></small>' ";
            body += "title=" + popupTitle + " data-content=" + popupContent + "/>";
            body += tree.getLevel() == 0 || isStandardView() ? "</br><div class='user-name'>" + user.getName() + " " + user.getSurname() + "</div>" : "";
        }
        String childPlace = "";
        if (tree.isChild() || (!user.isChild() && isActive())) {
            childPlace = "<ul>" + getPointForUser(tree) + "</ul>";
        } else if (user.isChild()) {
            childPlace = "<ul><li userId='"+tree.getId()+"' class='user-photo' style='width:100%; padding: 0;'><img src='/resources/images/users.png' style='width:"+ iconWidth +"px'/></li></ul>";
        }
        return "<li style='width:" + calculateNodeWidth(tree.getLevel()) + "px'>" + body + childPlace + "</li>";
    }

    public String getStubNode(BinaryTree tree, String clazz) {
        if (!isActive())
            return "<div style='width:" + calculateNodeWidth(tree.getLevel() + 1) + "px; float: left; padding: 20px 5px;'></div>";
        String image = "<img class=stub-node parentId=" + tree.getId() + " width='" + getIconWidth() + "px'";
        image += " position=" + (clazz.equals(RIGHT_TREE) ? Position.RIGHT.toString() : Position.LEFT.toString());
        image += " src=/resources/images/add-user.png title=" + this.stubTextTitle + " data-content=" + this.stubTextContent + "/>";
        return "<li style=width:" + calculateNodeWidth(tree.getLevel() + 1) + "px>" + image + "</li>";
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
        this.stubTextTitle = " '<div>" + stubTextTitle + "</div>'";
        this.stubTextContent = " '<small>" + stubTextContent + "</small>'";
    }

    public void setStatus(String activeStatus, String inactiveStatus) {
        this.activeStatus = activeStatus;
        this.inactiveStatus = inactiveStatus;
    }

    private int getIconWidth() {
        return isStandardView() ? STUB_WIDTH_HUGE : STUB_WIDTH_SMALL;
    }

    private boolean isStandardView() {
        return this.mode.equals("huge");
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
