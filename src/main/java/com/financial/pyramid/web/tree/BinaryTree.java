package com.financial.pyramid.web.tree;

import com.financial.pyramid.domain.User;
import com.financial.pyramid.web.form.UserForm;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * User: Danil
 * Date: 07.08.13
 * Time: 20:55
 */
public class BinaryTree {
    private Long id;
    private UserForm value;
    private BinaryTree parent;
    private BinaryTree left;
    private BinaryTree right;

    public BinaryTree(User user, int maxLevel) {
        this.id = user.getId();
        this.value = new UserForm(user.getName(), user.getSurname(), user.getPhoneNumber(), user.getPhoto());
        if(maxLevel == 0) return;
        if (user.getLeftChild() != null) {
            this.left = new BinaryTree(user.getLeftChild(), maxLevel - 1);
            this.left.setParent(this);
        }
        if (user.getRightChild() != null) {
            this.right = new BinaryTree(user.getRightChild(), maxLevel - 1);
            this.right.setParent(this);
        }
    }

    public Long getId() {
        return id;
    }

    public boolean isParent() {
        return parent != null;
    }

    public BinaryTree getParent() {
        return parent;
    }

    public void setParent(BinaryTree parent) {
        this.parent = parent;
    }

    public UserForm getValue() {
        return value;
    }

    public boolean isLeft() {
        return left != null;
    }

    public boolean itIsLeft() {
        return this.isParent() && this.getParent().isLeft() && this.getParent().getLeft().getId().equals(this.getId());
    }

    public BinaryTree getLeft() {
        return left;
    }

    public boolean isRight() {
        return right != null;
    }

    public boolean itIsRight() {
        return this.isParent() && this.getParent().isRight() && this.getParent().getRight().getId().equals(this.getId());
    }

    public BinaryTree getRight() {
        return right;
    }

    public boolean isChild() {
        return left != null || right != null;
    }

    public int getDepth() {
        int lDepth = this.isLeft() ? this.getLeft().getDepth() : 0;
        int rDepth = this.isRight() ? this.getRight().getDepth() : 0;
        int depth = lDepth > rDepth ? lDepth : rDepth;
        return depth + 1;
    }

    public int getLevel() {
        if (!this.isParent()) return 0;
        return this.getParent().getLevel() + 1;
    }
}