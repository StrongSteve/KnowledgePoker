package com.knowledgepoker.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "player")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_seq_gen")
    @SequenceGenerator(name="player_seq_gen", sequenceName="seq_player")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "passwordhash", nullable = false)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "socialusername", nullable = true)
    private String socialUserName;

    @Column(name = "socialuser", nullable = true)
    private boolean isSocialUser = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getDisplayName() {
        if (isSocialUser && !StringUtils.isEmpty(socialUserName)) {
            return socialUserName;
        }
        else
        {
            return userName;
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setSocialUserName(String socialUserName) {
        this.socialUserName = socialUserName;
    }

    public Boolean getSocialUser() {
        return isSocialUser;
    }

    public void setSocialUser(Boolean socialUser) {
        isSocialUser = socialUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName.replaceFirst("@.*", "@***") +
                ", passwordHash='" + passwordHash.substring(0, 10) +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.id, ((User)obj).id);
    }

    @Override
    public int hashCode() {
        return ((id==null) ? 0 : id.intValue());
    }
}
