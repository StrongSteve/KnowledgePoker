package com.knowledgepoker.transfer;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by starke on 11.02.2016.
 */
public class ChangePasswordForm {

    @NotEmpty
    private String password = "";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
