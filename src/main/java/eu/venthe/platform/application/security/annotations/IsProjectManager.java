package eu.venthe.platform.application.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This role has limited control over the CI/CD system, including managing users, repositorys, pipelines, and integrations
 * within relevant repositorys.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("hasRole('MANAGER')")
//@Repeatable
public @interface IsProjectManager {
}
