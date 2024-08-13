package eu.venthe.platform.application.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This role has limited control over the CI/CD system, including managing users, projects, pipelines, and integrations
 * within relevant projects.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("hasRole('MANAGER')")
//@Repeatable
public @interface IsProjectManager {
}
