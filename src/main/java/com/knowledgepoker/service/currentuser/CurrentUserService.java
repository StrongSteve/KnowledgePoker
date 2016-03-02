package com.knowledgepoker.service.currentuser;

import com.knowledgepoker.transfer.CurrentUser;

public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, Long userId);

}
