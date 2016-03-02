package com.knowledgepoker.service.user;

import com.knowledgepoker.domain.User;
import com.knowledgepoker.transfer.UserCreateForm;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByUserName(String email);

    Collection<User> getAllUsers();

    void removeUser(Long id);

    User saveUser(UserCreateForm userCreateForm);

    void changePassword(Long id, String password);

}
