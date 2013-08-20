package com.financial.pyramid.domain.type;

/**
 * User: Danil
 * Date: 11.08.13
 * Time: 16:11
 */
public enum  Position {
    LEFT("LEFT"),
    RIGHT("RIGHT");

    private String name;

    private Position(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
