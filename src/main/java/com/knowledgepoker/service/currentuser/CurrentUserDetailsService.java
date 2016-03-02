package com.knowledgepoker.service.currentuser;

import com.knowledgepoker.transfer.CurrentUser;
import com.knowledgepoker.domain.User;
import com.knowledgepoker.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsService.class);

    private UserService userService;

    public CurrentUserDetailsService() {
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CurrentUser loadUserByUsername(String userName) throws UsernameNotFoundException {
        LOGGER.debug("Authenticating user with userName={}", userName.replaceFirst("@.*", "@***"));
        User user = userService.getUserByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with userName=%s was not found", userName)));
        return new CurrentUser(user);
    }

}
