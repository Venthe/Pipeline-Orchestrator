package eu.venthe.platform.application.security.annotations;

import java.lang.annotation.*;

/**
 * This role has full control over the CI/CD system, including managing users, repositorys, pipelines, and integrations.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("hasRole('ADMINISTRATOR')")
//@Repeatable
public @interface IsSystemAdministrator {
}
