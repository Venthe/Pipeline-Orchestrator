package eu.venthe.pipeline.application.security;

import org.springframework.stereotype.Service;

import static eu.venthe.pipeline.application.security.User.ADMIN;

@Service
public class UserService {
    public User getCurrentUser() {
        return ADMIN;
    }
}
