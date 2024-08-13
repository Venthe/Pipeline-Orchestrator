package eu.venthe.pipeline.application.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Quality assurance engineers responsible for testing applications deployed through CI/CD
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@PreAuthorize("hasRole('QUALITY_ASSURANCE')")
//@Repeatable
public @interface IsQualityAssurance {
}
