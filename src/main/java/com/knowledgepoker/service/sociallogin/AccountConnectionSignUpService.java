package com.knowledgepoker.service.sociallogin;

import com.knowledgepoker.domain.Role;
import com.knowledgepoker.domain.User;
import com.knowledgepoker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import java.util.UUID;

public class AccountConnectionSignUpService implements ConnectionSignUp {

    private static final Logger LOG = LoggerFactory.getLogger(AccountConnectionSignUpService.class);

    private UserRepository userRepository;

    public AccountConnectionSignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String execute(Connection<?> connection) {
        org.springframework.social.connect.UserProfile profile = connection.fetchUserProfile();
        String userId = UUID.randomUUID().toString();
        User newUser = new User();
        newUser.setUserName(userId);
        newUser.setPasswordHash("socialuser");
        newUser.setRole(Role.USER);
        newUser.setSocialUserName(profile.getName());
        newUser.setSocialUser(true);
        userRepository.save(newUser);
        return userId;
    }
}