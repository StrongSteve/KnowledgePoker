package com.knowledgepoker.service.user;

import com.knowledgepoker.transfer.UserConnection;

/**
 * Created by starke on 22.02.2016.
 */
public interface SocialConnectionService {
    public UserConnection getSocialConnectionForUserWithName(String userName);
}
