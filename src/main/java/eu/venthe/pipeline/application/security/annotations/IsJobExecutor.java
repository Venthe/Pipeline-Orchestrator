package eu.venthe.pipeline.application.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Users who need read-only access to view project and pipeline information.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("hasRole('JOB_EXECUTOR')")
//@Repeatable
public @interface IsJobExecutor {
}
