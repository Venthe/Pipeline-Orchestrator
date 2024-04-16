package eu.venthe.pipeline.orchestrator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.togglz.core.user.NoOpUserProvider;
import org.togglz.core.user.UserProvider;

@Configuration
public class TogglzConfiguration {
    @Bean
    public UserProvider userProvider() {
        //return new org.togglz.spring.security.SpringSecurityUserProvider("ADMIN_AUTHORITY");
        return new NoOpUserProvider();
    }
}
