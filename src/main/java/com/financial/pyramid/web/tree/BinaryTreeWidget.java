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
    private String stubText;
    private String infoText;
    private boolean addEnabled = true;

    public void initTree(BinaryTree tree) {
        if (tree == null) {
            this.rootElement = "";
            return;
        }
        this.rootElementWidth = calculateTotalWidth(tree);
        this.rootElement = getUserNode(tree, "user-tree");
    }

    private double calculateTotalWidth(BinaryTree tree) {
        return Math.pow(2, tree.getDepth()) * 50;
    }

    private double calculateNodeWidth(BinaryTree tree) {
        return rootElementWidth / Math.pow(2, tree.getLevel());
    }

    public String getRootElement() {
        return rootElement;
    }

    public String getUserNode(BinaryTree tree, String clazz) {
        UserForm user = tree.getValue();
        boolean both = this.addEnabled;
        String result = "<div class='" + clazz + "' style='width:" + calculateNodeWidth(tree) + "px'>";
        result += "<div class='user-info''>";
        result += "<img src='/resources/images/vcard.png' title='" + this.infoText + "' alt='" + this.infoText + "'><br>";
        result += user.getSurname();
        result += " <br>";
        result += user.getName();
        result += "</div>";
        result += ((tree.isLeft() || tree.isRight() || both) ? "<canvas class='user-pointer' " +
                "drawLeft='" + (tree.isLeft() || both) + "' " +
                "drawRight='" + (tree.isRight() || both) + "'>" +
                "</canvas>" : "");
        result += "<div class='children-container'>" + getPointForUser(tree) + "</div>";
        result += "</div>";
        return result;
    }

    public String getStubNode(BinaryTree user, String clazz) {
        String result = "<div class='" + clazz + " stub-info user-info' ";
        result += "style='width:" + calculateNodeWidth(user) / 2 + "px'>";
        if (this.addEnabled) {
            result += "<img class='stub-node' parentId=" + user.getId();
            result += " position=" + (clazz.equals(RIGHT_TREE) ? Position.RIGHT.toString() : Position.LEFT.toString());
            result += " src='/resources/images/add-user.jpg' title='" + this.stubText + "' alt='" + this.stubText + "'>";
            result += "<br>";
        } else {
            result += "&nbsp;";
        }
        result += "</div>";
        return result;
    }

    public String getPointForUser(BinaryTree user) {
        String point = "I";
        return point + user.getId() + point;
    }

    public void addUserToWidget(BinaryTree user) {
        String result = user.isLeft() ? getUserNode(user.getLeft(), LEFT_TREE) : getStubNode(user, LEFT_TREE);
        result += user.isRight() ? getUserNode(user.getRight(), RIGHT_TREE) : getStubNode(user, RIGHT_TREE);
        rootElement = rootElement.replaceFirst(getPointForUser(user), result);
    }

    public void setStubText(String stubText) {
        this.stubText = stubText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public void setAddEnabled(boolean addEnabled) {
        this.addEnabled = addEnabled;
    }
}
