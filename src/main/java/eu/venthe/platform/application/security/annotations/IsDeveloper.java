package eu.venthe.platform.application.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Developers responsible for writing code and configuring CI/CD pipelines for their repositorys.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("hasRole('DEVELOPER')")
//@Repeatable
public @interface IsDeveloper {
}
