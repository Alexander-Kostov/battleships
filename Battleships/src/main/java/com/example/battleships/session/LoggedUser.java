package com.example.battleships.session;

import com.example.battleships.models.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class LoggedUser {
    private long id;
    private String fullName;

    public void logout() {
        this.id = 0;
        this.fullName = null;
    }

    public void login(User user) {
        this.fullName = user.getFullName();
        this.id = user.getId();
    }

    public long getId() {
        return id;
    }

    public LoggedUser setId(long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public LoggedUser setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

}
