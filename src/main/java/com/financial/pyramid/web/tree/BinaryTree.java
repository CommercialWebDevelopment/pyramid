package com.financial.pyramid.web.tree;

/**
 * User: Danil
 * Date: 07.08.13
 * Time: 20:55
 */
public class BinaryTree<T> {
    private Long id;
    private T value;
    private BinaryTree parent;
    private BinaryTree left;
    private BinaryTree right;

    public BinaryTree(Long id, T value) {
        this.id = id;
        this.value = value;
    }

    public BinaryTree(Long id, T value, BinaryTree left, BinaryTree right) {
        this.id = id;
        this.value = value;
        if(left != null) left.setParent(this);
        if(right != null) right.setParent(this);
        this.left = left;
        this.right = right;
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

    public T getValue() {
        return value;
    }

    public boolean isLeft() {
        return left != null;
    }

    public boolean itIsLeft() {
        return this.isParent() && this.getParent().getLeft() == this;
    }

    public BinaryTree getLeft() {
        return left;
    }

    public boolean isRight() {
        return right != null;
    }

    public boolean itIsRight() {
        return this.isParent() && this.getParent().getRight() == this;
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
        if(!this.isParent()) return 0;
        return this.getParent().getLevel() + 1;
    }
}