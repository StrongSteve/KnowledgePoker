package com.knowledgepoker.controller.interceptor;

import com.knowledgepoker.repository.SocialConnectionRepository;
import com.knowledgepoker.transfer.UserConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author starke
 */
@Component
public class SocialInterceptorUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SocialInterceptorUtil.class);

    private static final String USER_CONNECTION = "MY_USER_CONNECTION";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SocialConnectionRepository socialConnectionRepository;

    public void setModel(HttpServletRequest request, ModelAndView modelAndView) {

        String userId = request.getRemoteUser();

        String path = request.getRequestURI();
        HttpSession session = request.getSession();

        UserConnection connection = null;

        // Collect info if the user is logged in, i.e. userId is set
        if (userId != null) {

            // Get the current UserConnection from the http session
            connection = getUserConnection(session, userId);
        }

        // Update the model with the information we collected
        modelAndView.addObject("currentUserId",          userId);
        modelAndView.addObject("currentUserConnection",  connection);
    }

    /**
     * Get the current UserConnection from the http session
     *
     * @param session
     * @param userId
     * @return
     */
    public UserConnection getUserConnection(HttpSession session, String userId) {
        UserConnection connection;
        connection = (UserConnection) session.getAttribute(USER_CONNECTION);

        try {
            // Reload from persistence storage if not set or invalid (i.e. no valid userId)
            if (connection == null || !userId.equals(connection.getUserId())) {
                connection = socialConnectionRepository.getUserConnection(userId);
                session.setAttribute(USER_CONNECTION, connection);
            }
        } catch (EmptyResultDataAccessException ex) {}
        return connection;
    }
}
