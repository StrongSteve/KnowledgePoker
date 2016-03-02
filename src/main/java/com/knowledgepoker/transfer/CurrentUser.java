package com.knowledgepoker.transfer;

import com.knowledgepoker.domain.Role;
import com.knowledgepoker.domain.User;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.social.security.SocialUser;

public class CurrentUser extends SocialUser {

    private User user;

    public CurrentUser(User user) {
        super(user.getUserName(), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public Role getRole() {
        return user.getRole();
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}
