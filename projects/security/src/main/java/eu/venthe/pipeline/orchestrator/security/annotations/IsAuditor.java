package eu.venthe.pipeline.orchestrator.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Users responsible for auditing CI/CD activities for compliance and security purposes.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("hasRole('AUDITOR')")
//@Repeatable
public @interface IsAuditor {
}
