package com.knowledgepoker.service.user;

import com.knowledgepoker.domain.Role;
import com.knowledgepoker.domain.User;
import com.knowledgepoker.transfer.UserCreateForm;
import com.knowledgepoker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserById(long id) {
        LOGGER.debug("Getting user={}", id);
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Override
    public Optional<User> getUserByUserName(String email) {
        LOGGER.debug("Getting user by userName={}", email.replaceFirst("@.*", "@***"));
        return userRepository.findOneByUserName(email);
    }

    @Override
    public Collection<User> getAllUsers() {
        LOGGER.debug("Getting all users");
        return userRepository.findAll(new Sort("userName"));
    }

    @Override
    public void removeUser(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User saveUser(UserCreateForm userCreateForm) {
        User user = new User();
        user.setUserName(userCreateForm.getUserName());
        user.setPasswordHash(new BCryptPasswordEncoder().encode(userCreateForm.getPassword()));
        user.setRole(userCreateForm.getAdministrator() ? Role.ADMIN : Role.USER);
        return userRepository.save(user);
    }

    @Override
    public void changePassword(Long id, String password) {
        User user = userRepository.getOne(id);
        user.setPasswordHash(new BCryptPasswordEncoder().encode(password));
        userRepository.save(user);
    }

}
