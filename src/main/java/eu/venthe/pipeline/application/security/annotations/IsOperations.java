package eu.venthe.pipeline.application.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Operations personnel responsible for monitoring and maintaining the CI/CD infrastructure.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("hasRole('OPERATIONS')")
//@Repeatable
public @interface IsOperations {
}
