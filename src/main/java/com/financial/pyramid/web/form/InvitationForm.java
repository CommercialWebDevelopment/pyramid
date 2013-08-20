package com.financial.pyramid.web.form;

/**
 * User: Danil
 * Date: 12.08.13
 * Time: 21:18
 */
public class InvitationForm {
    private Long parentId;
    private String email;
    private String position;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}