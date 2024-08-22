package eu.venthe.platform.application.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Users who need read-only access to view repository and pipeline information.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("hasRole('VIEWER')")
//@Repeatable
public @interface IsViewer {
}
