package eu.venthe.pipeline.orchestrator.security;

import org.springframework.stereotype.Service;

import static eu.venthe.pipeline.orchestrator.security.User.ADMIN;

@Service
public class UserService {
    public User getCurrentUser() {
        return ADMIN;
    }
}
