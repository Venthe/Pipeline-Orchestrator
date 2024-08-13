package eu.venthe.platform.application.security;

import org.springframework.stereotype.Service;

import static eu.venthe.platform.application.security.User.ADMIN;

@Service
public class UserService {
    public User getCurrentUser() {
        return ADMIN;
    }
}
