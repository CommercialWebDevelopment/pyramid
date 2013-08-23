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

    public BinaryTreeWidget(BinaryTree tree) {
        if(tree == null) {
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
        return "<div class='"+clazz+"' style='width:"+ calculateNodeWidth(tree) +"px'>" +
                "<div class='user-info''>" +
                "<img src='/resources/images/vcard.png' alt='User Info'><br>" +
                user.getSurname() + " <br>" +
                user.getName() +
                "</div>" +
                "<canvas class='user-pointer'></canvas>" +
                "<div class='children-container'>"+getPointForUser(tree)+"</div>"+
                "</div>";
    }

    public String getStubNode(BinaryTree user, String clazz) {
        return "<div class='"+clazz+" user-info' style='width:"+ calculateNodeWidth(user) / 2+"px'>" +
                "<img class='stub-node' " +
                "parentId="+user.getId()+" " +
                "position="+ (clazz.equals(RIGHT_TREE) ? Position.RIGHT.toString() : Position.LEFT.toString()) +" " +
                "src='/resources/images/add-user.jpg' alt='Add user'><br>"+
                "</div>";
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
}
